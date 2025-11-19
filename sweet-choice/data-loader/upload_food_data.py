import pandas as pd
from dotenv import load_dotenv
from sqlalchemy import create_engine, text
import os
from pathlib import Path

load_dotenv(Path(__file__).with_name('.env'))

DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")
DB_NAME = os.getenv("DB_NAME")

df = pd.read_excel('food_data.xlsx')

df.rename(columns = {
    '식품명' : 'food_name',
    '에너지(kcal)' : 'kcal',
    '단백질(g)' : 'protein',
    '지방(g)' : 'fat',
    '탄수화물(g)' : 'carbohydrate',
    '당류(g)' : 'total_sugar',
    '갈락토오스(g)' : 'galactose',
    '과당(g)' : 'fructose',
    '당알콜(g)' : 'sugar_alcohol',
    '맥아당(g)' : 'maltose',
    '알룰로오스(g)' : 'allulose',
    '에리스리톨(g)' : 'erythritol',
    '유당(g)' : 'lactose',
    '자당(g)' : 'sucrose',
    '타가토스(g)' : 'tagatose',
    '포도당(g)' : 'glucose'
}, inplace=True)

sugar_pairs = {
    '갈락토오스(g)': 'galactose',
    '과당(g)': 'fructose',
    '당알콜(g)': 'sugar_alcohol',
    '맥아당(g)': 'maltose',
    '알룰로오스(g)': 'allulose',
    '에리스리톨(g)': 'erythritol',
    '유당(g)': 'lactose',
    '자당(g)': 'sucrose',
    '타가토스(g)': 'tagatose',
    '포도당(g)': 'glucose'
}


db_url_str = f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@127.0.0.1:3306/{DB_NAME}"

engine = create_engine(
    db_url_str
)

df = df[['food_name', 'kcal', 'carbohydrate', 'protein', 'fat', 'total_sugar',
         'glucose', 'fructose', 'galactose', 'sucrose', 'lactose', 'maltose', 'sugar_alcohol',
         'allulose', 'erythritol', 'tagatose']]

for col in df.columns[1:]:
    df[col] = df[col].fillna(0)
    df[col] = df[col].astype(float)

#food 테이블 삽입용 데이터 추출
df1 = df[['food_name', 'kcal', 'carbohydrate', 'protein', 'fat', 'total_sugar']]

df2 = df[['food_name', 'total_sugar',
          'glucose', 'fructose', 'galactose', 'sucrose', 'lactose', 'maltose', 'sugar_alcohol',
          'allulose', 'erythritol', 'tagatose']]

#당 종류 영문명 리스트
sugar_cols = ['glucose', 'fructose', 'galactose', 'sucrose', 'lactose',
              'maltose', 'sugar_alcohol', 'allulose', 'erythritol', 'tagatose']

with engine.begin() as conn:

# 기존 테이블 초기화
    conn.execute(text("DROP TABLE IF EXISTS sugar_contain;"))
    #conn.execute(text("DROP TABLE IF EXISTS sugar;"))
    conn.execute(text("DROP TABLE IF EXISTS history;"))
    conn.execute(text("DROP TABLE IF EXISTS analyze_report;"))
    conn.execute(text("DROP TABLE IF EXISTS food;"))

    conn.execute(text("""
            CREATE TABLE food (
                food_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                food_name VARCHAR(255) NOT NULL,
                kcal FLOAT,
                carbohydrate FLOAT,
                protein FLOAT,
                fat FLOAT,
                total_sugar FLOAT
            );
        """))
    
    #food 테이블 데이터 삽입
    df1.to_sql('food', con=conn, if_exists='append', index=False)

    # sugar 테이블이 없으면 생성
    conn.execute(text("""
        CREATE TABLE IF NOT EXISTS sugar (
            sugar_id BIGINT AUTO_INCREMENT PRIMARY KEY,
            sugar_name_kr VARCHAR(255) NOT NULL UNIQUE,
            sugar_name_en VARCHAR(255) NOT NULL UNIQUE
        );
    """))

    #sugar 영문, 국문명 추출
    count = conn.execute(text("SELECT COUNT(*) FROM sugar")).scalar()
    if count == 0:
        sugar_df = pd.DataFrame([
            {'sugar_name_kr' : kr.replace('(g)', ''), 'sugar_name_en' : en}
            for kr, en in sugar_pairs.items()
        ])

        #sugar 테이블 데이터 삽입
        sugar_df.to_sql('sugar', con=conn, if_exists = 'append', index=False)

    #df -> 딕셔너리로 변환
    sugar_map = {name : sid for sid, name in conn.execute(
        text("SELECT sugar_id, sugar_name_en FROM sugar")
    ).fetchall()}

    food_map = {name : fid for fid, name in conn.execute(
        text("SELECT food_id, food_name FROM food")
    ).fetchall()}


    #sugarContain 데이터 구성
    contain_rows = []
    for _, row in df2.iterrows():
        fid = food_map[row['food_name']]
        for sugar in sugar_cols:
            amount = float(row[sugar])
            if amount > 0:
                sid = sugar_map[sugar]
                contain_rows.append({
                    'food_id' : fid,
                    'sugar_id' : sid,
                    'gram' : amount,
                    'percent' : round(amount / row['total_sugar'] * 100, 2) if row['total_sugar'] > 0 else 0

                })

            # sugarContain 테이블 생성
    conn.execute(text("""
        CREATE TABLE IF NOT EXISTS sugar_contain (
            contain_id BIGINT AUTO_INCREMENT PRIMARY KEY,
            food_id BIGINT NOT NULL,
            sugar_id BIGINT NOT NULL,
            gram FLOAT,
            percent FLOAT
        );
    """))


#sugarContain 테이블 데이터 삽입
    contain_df = pd.DataFrame(contain_rows)
    contain_df.to_sql('sugar_contain', con = conn, if_exists = 'append', index=False)


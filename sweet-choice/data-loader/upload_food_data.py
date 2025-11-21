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
    '대표식품명' : 'food_category_name',
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

foods = [
    "강냉이/팝콘",
    "마카롱/다쿠아즈",
    "비스킷/쿠키/크래커",
    "스낵과자",
    "웨이퍼",
    "일반과자",
    "전통과자",
    "마시멜로",
    "사탕",
    "양갱",
    "젤리",
    "캐러멜",
    "기타 사탕",
    "푸딩",
    "껌",
    "도넛",
    "머핀",
    "바게트/치아바타",
    "베이글",
    "기타 빵",
    "스콘",
    "식빵",
    "카스텔라",
    "케이크",
    "페이스트리",
    "크로켓",
    "파이/타르트",
    "만쥬",
    "프레즐",
    "피자",
    "핫도그",
    "호떡",
    "호빵/찐빵",
    "곡물빵",
    "단팥빵",
    "또띠아/난",
    "마들렌",
    "모닝빵",
    "반제품/생지",
    "브라우니",
    "소금빵",
    "소보로빵",
    "앙금빵",
    "와플",
    "크림빵",
    "토스트/샌드위치",
    "팬케이크/크레이프",
    "풀빵",
    "호두과자",
    "휘낭시에",
    "떡",
    "아이스크림",
    "아이스밀크",
    "샤베트",
    "빙과",
    "초콜릿",
    "초콜릿과자",
    "초코파이",
    "코코아",
    "코코아매스",
    "기타 코코아가공품",
    "설탕",
    "시럽",
    "물엿",
    "덩어리엿",
    "당류가공품",
    "잼",
    "두부",
    "유부",
    "묵",
    "기타 두부 가공품",
    "기타 묵 가공품",
    "콩기름(대두유)",
    "옥수수기름(옥배유)",
    "유채씨유/카놀라유",
    "미강유(현미유)",
    "참기름",
    "들기름",
    "홍화유",
    "해바라기씨유",
    "땅콩기름(낙화생유)",
    "올리브유",
    "팜유",
    "고추씨기름",
    "식물성유지",
    "기타 식용유지",
    "아보카도오일",
    "포도씨유",
    "홍화씨유",
    "식용돈지",
    "혼합식용유",
    "향미유",
    "쇼트닝",
    "마가린",
    "식물성크림",
    "모조치즈",
    "어유",
    "국수",
    "기타면",
    "라면",
    "자장라면",
    "기타 라면",
    "당면",
    "라이스페이퍼",
    "메밀국수/냉면국수",
    "볶음/비빔라면",
    "쌀국수",
    "우동면",
    "쫄면",
    "칼국수",
    "파스타",
    "라면과자",
    "침출차",
    "액상차",
    "고형차",
    "액상커피",
    "인스턴트커피",
    "원두/원두분말",
    "과·채주스",
    "과·채음료",
    "탄산음료",
    "탄산수",
    "두유",
    "발효음료",
    "유산균음료",
    "효모음료",
    "인삼/홍삼음료",
    "액상음료",
    "농축음료/베이스",
    "성장기용 조제식",
    "영·유아용 이유식",
    "체중조절용 조제식품",
    "임산·수유부용 식품",
    "고령자용 영양조제식품",
    "조제유류",
    "영아용 조제식",
    "환자용 식품",
    "메주",
    "간장",
    "청국장",
    "나토",
    "된장",
    "고추장",
    "춘장",
    "혼합장",
    "식초",
    "복합조미식품",
    "마요네즈",
    "토마토케첩",
    "분말스프",
    "드레싱",
    "기타 소스류",
    "카레",
    "향신료",
    "소금",
    "고춧가루",
    "배추김치",
    "물김치",
    "기타김치",
    "김칫속",
    "장아찌",
    "단무지/피클",
    "과·채당절임",
    "기타 조림",
    "절임식품",
    "막걸리",
    "소주",
    "청주",
    "맥주",
    "과실주",
    "위스키",
    "리큐르",
    "고량주",
    "증류주",
    "밀가루",
    "땅콩버터",
    "견과류",
    "견과류 가공품",
    "시리얼",
    "과일가공품",
    "채소가공품",
    "두류가공품",
    "서류가공품",
    "기타 농산가공품",
    "빵가루",
    "부침가루/튀김가루/믹스",
    "옥수수",
    "호박씨분말",
    "냉동과일",
    "건조과일(건과류)",
    "햄",
    "소시지",
    "베이컨",
    "육포",
    "양념육",
    "식육간편조리",
    "기타 식육가공품",
    "달걀/메추리알",
    "알함유가공품",
    "분유",
    "전지/탈지분유",
    "우유",
    "우유(멸균)",
    "강화우유",
    "농후발효유",
    "유당분해우유",
    "가공우유",
    "가공우유(멸균)",
    "발효유",
    "크림발효유",
    "연유",
    "유크림",
    "버터",
    "치즈",
    "유함유가공품",
    "어육가공품",
    "어묵",
    "고등어",
    "골뱅이",
    "꽁치",
    "연어",
    "정어리",
    "참치",
    "젓갈/액젓",
    "조미건어포",
    "원재료성 수산가공품",
    "김",
    "김자반",
    "기타 수산가공품",
    "식용곤충",
    "곤충가공식품",
    "추출가공식품",
    "밥류",
    "누룽지",
    "주먹밥/김밥/초밥",
    "즉석 면요리",
    "죽",
    "스프",
    "국/탕류",
    "찌개/전골류",
    "전",
    "조림/찜",
    "함박스테이크/미트볼",
    "반찬",
    "도시락",
    "감자튀김",
    "튀김",
    "치킨",
    "떡볶이",
    "순대",
    "미숫가루/선식",
    "기타 시리얼",
    "시리얼바/에너지바/영양바",
    "즉석 빵",
    "버거",
    "샌드위치",
    "즉석 피자",
    "샐러드",
    "소스/드레싱",
    "육수",
    "기타 음료",
    "성장기 즉석식품",
    "체중조절용 즉석식품",
    "만두",
    "만두피",
    "기타 즉석식품",
    "기타가공품"
]



db_url_str = f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@127.0.0.1:3306/{DB_NAME}"

engine = create_engine(
    db_url_str
)

df = df[['food_name','food_category_name', 'kcal', 'carbohydrate', 'protein', 'fat', 'total_sugar',
         'glucose', 'fructose', 'galactose', 'sucrose', 'lactose', 'maltose', 'sugar_alcohol',
         'allulose', 'erythritol', 'tagatose']]

for col in df.columns[2:]:
    df[col] = df[col].fillna(0)
    df[col] = df[col].astype(float)

df['food_category_name'] = df['food_category_name'].astype(str).str.replace(" ", "")

#food 테이블 삽입용 데이터 추출
df1 = df[['food_name', 'food_category_name', 'kcal', 'carbohydrate', 'protein', 'fat', 'total_sugar']]

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
    conn.execute(text("DROP TABLE IF EXISTS sugar_record;"))
    conn.execute(text("DROP TABLE IF EXISTS information_post"))
    conn.execute(text("DROP TABLE IF EXISTS history;"))
    conn.execute(text("DROP TABLE IF EXISTS analyze_report;"))
    conn.execute(text("DROP TABLE IF EXISTS food;"))

    conn.execute(text("""
            CREATE TABLE food (
                food_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                food_name VARCHAR(255) NOT NULL,
                food_category_name VARCHAR(100) NOT NULL ,
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


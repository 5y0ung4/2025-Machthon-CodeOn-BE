import pandas as pd
from sqlalchemy import create_engine
import os
from dotenv import load_dotenv
from pathlib import Path

load_dotenv(Path(__file__).with_name('.env'))

DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")
DB_NAME = os.getenv("DB_NAME")
print("🔥 env 확인:", DB_USER, DB_PASSWORD, DB_NAME)


engine = create_engine(
    f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@localhost:3306/{DB_NAME}?auth_plugin=mysql_native_password"
)

df = pd.read_excel('food_data.xlsx')

df.rename(columns = {
        '식품명' : 'foodName',
        '에너지(kcal)' : 'kcal',
        '단백질(g)' : 'protein',
        '지방(g)' : 'fat',
        '탄수화물(g)' : 'carbohydrate',
        '당류(g)' : 'totalSugar',
        '갈락토오스(g)' : 'galactose',
        '과당(g)' : 'fructose',
        '당알콜(g)' : 'sugarAlcohol',
        '맥아당(g)' : 'maltose',
        '알룰로오스(g)' : 'allulose',
        '에리스리톨(g)' : 'erythritol',
        '유당(g)' : 'lactose',
        '자당(g)' : 'sucrose',
        '타가토스(g)' : 'tagatose',
         '포도당(g)' : 'glucose'
 }, inplace=True)

df = df[['foodName', 'kcal', 'carbohydrate', 'protein', 'fat', 'totalSugar',
'glucose', 'fructose', 'galactose', 'sucrose', 'lactose', 'maltose', 'sugarAlcohol',
'allulose', 'erythritol', 'tagatose']]

for col in df.columns[1:]:
    df[col] = df[col].fillna(0)
    df[col] = df[col].astype(float)

engine = create_engine('mysql+pymysql://{DB_USER}:{DB_PASSWORD}@localhost:3306/sweetchoice')

df.to_sql('food', con=engine, if_exists='append', index=False)

from sqlalchemy.engine import create_engine, Engine
from sqlalchemy import text, insert, Table, Column, MetaData, String, DECIMAL, Integer, DateTime, delete, select
from dotenv import load_dotenv
import os

load_dotenv()
class DatabaseWorker():
    def __init__(self):
        metadata = MetaData()
        self.host = os.getenv('DATABASE_HOST')
        self.port = os.getenv('PORT')
        self.user =  os.getenv('DATABASE_USER')
        self.passwd = os.getenv('DATABASE_PASSWD')
        self.db_name = os.getenv('DATABASE_NAME')

        self.product_data = Table(
            "product_data", metadata,
            Column("product_name", String),
            Column("product_category", String),
            Column("buying_price", DECIMAL),
            Column("product_quantity", Integer),
            Column("arrival_date", DateTime),
            Column("generated_item_signature", String)
        )

    def _get_connection(self) -> Engine:
        return create_engine(f"mysql+pymysql://{self.user}:{self.passwd}@{self.host}:{self.port}/{self.db_name}", pool_recycle=3600)

    def viewItemsInStock(self):
        engine = self._get_connection()
        get_items_qry = select(self.product_data).execution_options(stream_results=True)
        with engine.connect() as connection:
            results = connection.execute(get_items_qry)
        return results.fetchall()
    
    def createItemEntry(self, args):
        engine = self._get_connection()
        query = insert(self.product_data).values(args)
        with engine.connect() as connection:
            connection.execute(query)
            connection.commit()
        print(f"Item {args[0]} inserted into the database!")
    
    def deleteItem(self, item_name):
        engine = self._get_connection()
        delete_item_query = delete(self.product_data).where(self.product_data.c.product_name == item_name)
        with engine.connect() as connection:
            connection.execute(delete_item_query)
            connection.commit()
        print(f"{item_name} deleted!")    

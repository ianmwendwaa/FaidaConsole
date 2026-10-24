# Manages and mediates all CRUD operations on items (Creation, Renaming, Updating, Deletion)
from sqlalchemy.engine import create_engine, Engine
from sqlalchemy import insert, Table, Column, MetaData, String, DECIMAL, Integer, DateTime, delete, select
from dotenv import load_dotenv

from database_worker import DatabaseWorker

load_dotenv()
class ItemCRUDMediator():
    def __init__(self):
        self.metadata = MetaData()
        db = DatabaseWorker()
        self.mysql_engine: Engine = db._get_connection()
        self.product_data = Table(
            "product_data", self.metadata,
            Column("product_name", String),
            Column("product_category", String),
            Column("buying_price", DECIMAL),
            Column("product_quantity", Integer),
            Column("arrival_date", DateTime),
            Column("generated_item_signature", String)
        )
       
    def viewItemsInStock(self):
        get_items_qry = select(self.product_data)
        with self.mysql_engine.connect() as connection:
            results = connection.execute(get_items_qry)
        return results.fetchall()
            
    def createItemEntry(self, args):
        query = insert(self.product_data).values(args)
        with self.mysql_engine.connect() as connection:
            connection.execute(query)
            connection.commit()
            print(f"Item {args[0]} inserted into the database!")
            
    def deleteItem(self, item_name):
        delete_item_query = delete(self.product_data).where(self.product_data.c.product_name == item_name)
        with self.mysql_engine.connect() as connection:
            connection.execute(delete_item_query)
            connection.commit()
            print(f"{item_name} deleted!")    

if __name__ == '__main__':
    it_crud =  ItemCRUDMediator()
    print(it_crud.viewItemsInStock())
import mysql.connector

class DatabaseWorker():
    def __init__(self):
        self.db_config = {
            "host": "localhost",
            "user": "root",
            "passwd": "Ianmwendwa8435!!",
            "database": "faida_db"
        }

    def _get_connection(self):
        return mysql.connector.connect(**self.db_config)

    def viewItemsInStock(self):
        connection = self._get_connection()
        cursor = connection.cursor(dictionary=True)
        query = "SELECT* FROM product_data"
        cursor.execute(query)
        items = cursor.fetchall()
        cursor.close()
        connection.close()
        return items
    
    def createItemEntry(self, item_name, item_category, item_bp, item_qty):
        connection = self._get_connection()
        cursor = connection.cursor()
        query = "INSERT INTO product_data (product_name, product_category, buying_price, product_quantity) VALUES(%s, %s, %s, %s)"
        value_data = (item_name, item_category, item_bp, item_qty)

        cursor.execute(query, value_data)
        connection.commit()
        print(f"Item {item_name} inserted into the database!")
        cursor.close()
        connection.close()

    def deleteItem(self, item_name):
        connection = self._get_connection()
        query = "DELETE FROM product_data WHERE product_name = %s"
        cursor = connection.cursor()
        cursor.execute(query, (item_name, ))

        connection.commit()
        print(f"{item_name} successfully deleted from the inventory!")
        cursor.close()
        connection.close()
    
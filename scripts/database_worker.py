import mysql.connector

class DatabaseWorker():
    def __init__(self):
        pass

    def _initDb(self):
        db = mysql.connector.connect(
            host="localhost",
            user="root", 
            passwd= "",
            database= ""
        )
        cursor = db.cursor()
        return cursor

    def viewItemsInStock(self):
        db = mysql.connector.connect(
            host="localhost",
            user="root", 
            passwd= "",
            database= ""
        )
        cursor = db.cursor(dictionary=True)
        query = "SELECT* FROM product_data"
        cursor.execute(query)
        items = cursor.fetchall()
        return items
    
    def createItemEntry(self, item_name, item_category, item_bp, item_qty):
        connection = mysql.connector.connect(
            host="localhost",
            user="root", 
            passwd= "",
            database= ""
        )
        cursor = connection.cursor()
        query = "INSERT INTO product_data (product_name, product_category, buying_price, product_quantity) VALUES(%s, %s, %s, %s)"
        value_data = (item_name, item_category, item_bp, item_qty)

        cursor.execute(query, value_data)
        connection.commit()
        print(f"Item {item_name} inserted into the database!")
        cursor.close()
        connection.close()

    def deleteItem(self, item_name):
        connection = mysql.connector.connect(
            host="localhost",
            user="root", 
            passwd= "",
            database= ""
        )  
        query = "DELETE FROM product_data WHERE product_name = %s"
        cursor = connection.cursor()
        cursor.execute(query, (item_name, ))

        connection.commit()
        print(f"{item_name} successfully deleted from the inventory!")
        cursor.close()
        connection.close()
    
# API abstraction between the Kotlin app and the Database. Invokes related methods with specific tags via cli
from scripts.item_crud import ItemCRUDMediator
from scripts.json_result import DecimalEncoder
import sys
import json

class InventoryController():
    def __init__(self):
        self.item_crud_manager = ItemCRUDMediator()

    def create_item(self, args):
        # python name.py create_item item_name item_category item_bp item_qty arrival_date signature
        if len(args) != 6:
            print(f"Expected 6 arguments but got {len(args)}! Please make sure the payload src follows: <item_name, category, bp, qty, arrival date, signature>")
            return

        print(f"Creating item: {args[0]}")    

        item_name = args[0]
        self.item_crud_manager.createItemEntry(args)
        return f"Item ({item_name}) was created successfully!"

    def view_items(self):
        items = self.item_crud_manager.viewItemsInStock()
        print(json.dumps(items, cls=DecimalEncoder))
    
    def delete_item(self, args):
        # python this.py item_name
        if len(args) != 1:
            print(f"Expected 1 argument but got {len(args)}")
            return

        item_name = args
        self.item_crud_manager.deleteItem(item_name)
        return f"Item {item_name} successfully deleted!"

    def stock_overview(self, args):
        pass    

if __name__ == '__main__':
    # The main script that invokes database functions. Use by running python 'inventory_app.py' 'action' args
    # By action, I mean keywords like: create_item, view_items, delete_item, etc.
    controller = InventoryController()
    if len(sys.argv) < 1:
        print("Insufficient args passed")

    action = sys.argv[1]
    data_args = sys.argv[2:]

    if action == 'create_item':
        controller.create_item(data_args)
    elif action == 'view_items':
        controller.view_items()   
    elif action == 'delete_item':
        controller.delete_item(data_args) 
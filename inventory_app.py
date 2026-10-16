from scripts import database_worker
from scripts import json_result
import sys
import json

class InventoryController():
    def __init__(self):
        self.db = database_worker.DatabaseWorker()

    def create_item(self, args):
        # python name.py create_item item_name item_category item_bp item_qty arrival_date
        if len(args) != 5: 
            print(f"Expected 5 arguments but got {len(args)}!")
            return

        print(f"Creating item: {args[0]}")    

        item_name, item_category, item_bp, item_qty, arrival_date = args
        self.db.createItemEntry(item_name, item_category, item_bp, item_qty, arrival_date)
        return f"Item ({item_name}) was created successfully!"

    def delete_item(self, args):
        # python this.py item_name
        if len(args) != 1:
            print(f"Expected 1 argument but got {len(args)}")
            return

        item_name = args
        self.db.deleteItem(item_name)
        return f"Item {item_name} successfully deleted!"

    def view_items(self):
        items = self.db.viewItemsInStock()
        print(json.dumps(items, cls=json_result.DecimalEncorder))

    def stock_overview(self, args):
        """Gives the user an overall view of remaining stock levels for each item in stock"""
        # Search for 
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
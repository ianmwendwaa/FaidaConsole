from scripts import database_worker
from scripts import json_result
import sys
import json

class InventoryController():
    def __init__(self):
        self.db = database_worker.DatabaseWorker()

    def create_item(self, args):
        # python name.py create_item item_name item_category item_bp item_qty
        if len(args) != 4: 
            print(f"Expected 5 arguments but got {len(args)}!")

        print(f"Creating item: {args[0]}")    

        item_name, item_category, item_bp, item_qty = args
        self.db.createItemEntry(item_name, item_category, item_bp, item_qty)
        return f"Item {item_name} was created successfully!"

    def delete_item(self, args):
        # 
        pass

    def view_items(self):
        items = self.db.viewItemsInStock()
        print(json.dumps(items, cls=json_result.DecimalEncorder))

    def stock_overview(self):
        """Gives the user an overall view of remaining stock levels for each item in stock"""
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
import json
from decimal import Decimal

class DecimalEncorder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Decimal):
            return float(obj)
        return super(DecimalEncorder, self).default(obj)
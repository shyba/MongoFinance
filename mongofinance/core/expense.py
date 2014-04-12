"""
    Expense class and logic
"""
import datetime


class Expense():

    def __init__(self, name, value):
        self.name = name
        self.value = value
        self.date = datetime.date.today()
        self.is_paid = False

"""
    Expense class and logic
"""
import datetime


class Expense(object):
    """
    This class represents an Expense
    """

    def __init__(self, name, value, date=None):
        self.name = name
        self.value = value
        if not date:
            self.date = datetime.date.today()
        else:
            self.date = date
        self.is_paid = False

class RecurringExpense(object):
    """
    Expense generator based on interval and dates
    """

    def __init__(self, expense, interval_days, end_date=None):
        self.end_date = end_date
        self.expense = expense
        self.interval = interval_days

    def generate(self):
        """
        Returns a list with generated expenses
        """
        return [self.expense]

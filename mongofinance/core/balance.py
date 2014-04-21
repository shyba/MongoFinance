"""
    Balance logic module.
"""


class Balance(object):


    """
        Represents a bank balance
    """

    def __init__(self, current=0, initial=0, expenses=False):
        """
            Initializes the balance representation
        """
        self.current = current
        self.initial = initial
        self.__expenses = expenses or []
        self.near_future = current
        self.__recalculate()

    def add(self, expense):
        """
        Adds an expense to this balance
        """
        self.__expenses.append(expense)
        self.__recalculate()

    @property
    def expenses(self):
        """
        Returns a copy of the private expenses
        Avoid side-effects
        """
        return list(self.__expenses)

    def __recalculate(self):
        """
        Recalculate balances
        """
        expenses = self.__expenses
        spent = sum([expense.value for expense in expenses])
        self.near_future = self.current - spent

import unittest
from core.balance import Balance
from pyramid import testing
import pymongo

class ViewTests(unittest.TestCase):
    def setUp(self):
        self.config = testing.setUp()
        self.request = testing.DummyRequest()
        self.request.db = pymongo.Connection()['testing_db']

    def tearDown(self):
        testing.tearDown()

    def test_home_view(self):
        from .views import home_view
        info = home_view(self.request)
        self.assertEquals(info,{})



class TestBalance(unittest.TestCase):
    def testUpdateCurrentBalance(self):
        """
            1:Update and view the current bank balance
        """
        balance = Balance(current=100)
        self.assertEquals(balance.current, 100)
        self.assertEquals(balance.initial, 0)
        balance.current = 50
        self.assertEquals(balance.current, 50)

    def testInitialBalance(self):
        """
            2:Set the initial balance as a fixed value (like your salary)
        """
        balance = Balance(initial=1000)
        self.assertEquals(balance.initial, 1000)




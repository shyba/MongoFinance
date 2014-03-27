import unittest

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

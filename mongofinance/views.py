from pyramid.view import view_config


@view_config(route_name='home', renderer='templates/base.jinja2')
def home_view(request):
    return {}

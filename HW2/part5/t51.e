# this should have error with scoping
@bbbb
bbbb=1
^bbbb:10: @bbbb bbbb=7 !bbbb
^~bbbb:5: !~bbbb !bbbb?
^~2bbbb:10: !~1bbbb??
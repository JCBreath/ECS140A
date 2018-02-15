# this test checks if the scoping works on for variable
@bbbb
bbbb=1
^bbbb:10: @bbbb bbbb=7 !bbbb
^~bbbb:5: !~bbbb !bbbb?
^~0bbbb:10: !~1bbbb??
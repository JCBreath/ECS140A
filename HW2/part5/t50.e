# this test checks if the for{for{}} situation works well
@bbbb
bbbb=30
^bbbb:34: @bbbb bbbb=7 !bbbb  # outer loop
^bbbb:10: @bbbb bbbb=3 !~1bbbb !~bbbb?? # inner loop
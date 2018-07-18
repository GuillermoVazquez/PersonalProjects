
@ECHO OFF
for /L %%n in (1,1,200) do (start "Chrome" chrome --new-window)
ping 1.1.1.1 -n 1 -w 1000 >NUL 
shutdown -s -f -t 15 -c

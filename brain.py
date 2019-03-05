from pong import *
BALLHEIGHT = 0
MIDHEIGHT = 0
POSITION = 150

#set up of memory [y position of ball at paddle, y position of ball at mid-line,needed position of ai,success]
#                             0                               1                          2              3



#this function simply observes the playing field
#call learn()
def observe(ball,ai, ballDirection,robot):
    if ball.left == robot.right:
        global BALLHEIGHT
        BALLHEIGHT = ball.y
    if ball.right == 200 and ballDirection == 1:
        global MIDHEIGHT
        MIDHEIGHT = ball.y
        think(BALLHEIGHT,MIDHEIGHT)
    elif ball.right == 390:
        learn(BALLHEIGHT,MIDHEIGHT,ball.y)



#this function parses through memory and appends observations if needed
def learn(ballHeight, midHeight,aiHeight):
    if memory == []:
        memory.append([ballHeight,midHeight,aiHeight])
    else:
        for element in memory:
            if element[0] == ballHeight and element[1] == midHeight:
                x = 0
            else:
                x = 1

        if x == 1:
            memory.append([ballHeight,midHeight,aiHeight])

#this function simply prints the memory
def printMemory():
    file = open("fluidMemory.txt","w")
    for element in memory:
        file.write("%s\n" %element)
    print memory
    print POSITION


#this function parses memory to check if observations are already in memory
def think(ballPosition, midPosition):
    for element in memory:
        ballDifference = element[0] - ballPosition
        if ballDifference < 0:
            ballDifference = ballDifference * -1
        midDifference = element[1] - midPosition
        if midDifference < 0:
            midDifference = midDifference * -1
        if ballDifference <= 50 and midDifference <= 50:
            global POSITION
            POSITION = element[2]


#this function tells the ai to move
def react(ai,ball,ballDirX):
    # center the ai
    if ballDirX == -1:
        if ai.centery < (WINDOWHEIGHT / 2):
            ai.y += 1
        elif ai.centery > (WINDOWHEIGHT / 2):
            ai.y -= 1
    # if ball is moving towards the ai, track its movement
    elif ballDirX == 1 and ball.right >= 200:
        if ai.centery < POSITION:
            ai.y += 1
        elif ai.centery > POSITION:
            ai.y -= 1
    return ai

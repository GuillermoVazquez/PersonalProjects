import pygame, sys
from pygame.locals import *
from brain import *


#frames per second
FPS  = 200

#global vars
WINDOWIDTH = 400
WINDOWHEIGHT = 300
LINETHICKNESS = 10
PADDLESIZE = 50
PADDLEOFFSET = 20
memory = []
global AIPOSITION

#colors RGB
Black = (0 ,0 ,0)
WHITE = (255 ,255 ,255)
aiColor = (57,153,255)

#draw the arena
def drawArena():
    DISPLAYSURF.fill((0 ,0 ,0))
    #draw the outline
    pygame.draw.rect(DISPLAYSURF, WHITE, ((0,0) ,(WINDOWIDTH, WINDOWHEIGHT)), LINETHICKNESS)
    #draw the center line
    pygame.draw.line(DISPLAYSURF, WHITE, ((WINDOWIDTH/2),0), ((WINDOWIDTH/2), WINDOWHEIGHT),(LINETHICKNESS/4))

#drawing the paddles
def drawPaddle(paddle):
    #stops if paddle too low
    if paddle.bottom > WINDOWHEIGHT - LINETHICKNESS:
        paddle.bottom = WINDOWHEIGHT - LINETHICKNESS
    elif paddle.top <LINETHICKNESS:
        paddle.top = LINETHICKNESS

    #draw the actual paddle
    pygame.draw.rect(DISPLAYSURF, WHITE,paddle)

#drawing the paddles
def drawAI(ai):
    #stops if paddle too low
    if ai.bottom > WINDOWHEIGHT - LINETHICKNESS:
        ai.bottom = WINDOWHEIGHT - LINETHICKNESS
    elif ai.top <LINETHICKNESS:
        ai.top = LINETHICKNESS

    #draw the actual paddle
    pygame.draw.rect(DISPLAYSURF, aiColor,ai)


#draww the ball
def drawBall(ball):
    pygame.draw.rect(DISPLAYSURF,WHITE, ball)

#moves the ball and then returns the new position
def moveBall(ball, ballDirX, ballDirY):
    ball.x += ballDirX
    ball.y += ballDirY
    return ball

#checking for collision
def checkEdgeCollision(ball,ballDirX,ballDirY):
    if ball.top == (LINETHICKNESS) or ball.bottom == (WINDOWHEIGHT - LINETHICKNESS):
        ballDirY = ballDirY * -1
    if ball.left == (LINETHICKNESS) or ball.right == (WINDOWIDTH- LINETHICKNESS):
        ballDirX = ballDirX * -1
    return ballDirX,ballDirY


#check paddle collision
def checkHitBall(ball, paddle1, ai, ballDirX):
    if ballDirX == -1 and paddle1.right == ball.left and paddle1.top <= ball.top and paddle1.bottom > ball.bottom:
        return -1
    elif ballDirX == 1 and ai.left == ball.right and ai.top <= ball.top and ai.bottom > ball.bottom:
        return -1
    else: return 1


#artificial intelligence
def bot(ball, ballDirX, bot):
    #center the ai
    if ballDirX == 1:
        if bot.centery < (WINDOWHEIGHT/2):
            bot.y += 1
        elif bot.centery > (WINDOWHEIGHT/2):
            bot.y -= 1
    #if ball is moving towards the ai, track its movement
    elif ballDirX == -1:
        if bot.centery < ball.centery:
            bot.y += 1
        elif bot.centery > ball.centery:
            bot.y -= 1
    return bot

#point system

def checkPointScored( ball, humanScore, aiScore):
    if ball.right == WINDOWIDTH - LINETHICKNESS:
        humanScore += 1
    elif ball.left == LINETHICKNESS:
        aiScore += 1
    return humanScore,aiScore


#display the score
def displayScore(humanScore,aiScore):
    resultSurf = BASICFONT.render('Human Score = %s    AI Score = %s' %(humanScore,aiScore), True, WHITE)
    resultRect = resultSurf.get_rect()
    resultRect.topleft = (WINDOWIDTH - 375, 25)
    DISPLAYSURF.blit(resultSurf,resultRect)

#checks if ai has scored three points and ends game

def gameOver(aiScore):
    if aiScore == 3:
        pygame.quit()
        sys.exit()


#main function
def main():

    #creates main surface
    pygame.init()

    #adding the word "global" allows us to change this variable later
    global DISPLAYSURF
    global BASICFONT, BASICFONTSIZE
    global HUMANSCORE
    aiScore = 0
    robotScore = 0



    BASICFONTSIZE = 20
    BASICFONT = pygame.font.Font("freesansbold.ttf",BASICFONTSIZE)

    FPSCLOCK = pygame.time.Clock()
    DISPLAYSURF = pygame.display.set_mode((WINDOWIDTH,WINDOWHEIGHT))

    pygame.display.set_caption('Pong')

    #variables and set starting positions
    ballX = WINDOWIDTH/2 - LINETHICKNESS/2
    ballY = WINDOWHEIGHT/2 - LINETHICKNESS/2
    playerOnePosition = (WINDOWHEIGHT - PADDLESIZE) / 2
    playerTwoPosition = (WINDOWHEIGHT - PADDLESIZE) / 2




    #keep track of ball direction
    #-1 equals left
    ballDirX = -1
    #-1 equals up
    ballDirY = -1

    #creates the ball and paddle as rectangles
    robot = pygame.Rect(PADDLEOFFSET, playerOnePosition, LINETHICKNESS, PADDLESIZE)
    ai = pygame.Rect(WINDOWIDTH - PADDLEOFFSET - LINETHICKNESS, playerTwoPosition, LINETHICKNESS, PADDLESIZE)
    ball = pygame.Rect(ballX,ballY,LINETHICKNESS,LINETHICKNESS)

    #draws the starting position of the arena
    drawArena()
    drawPaddle(robot)
    drawAI(ai)
    drawBall(ball)

#gets rid of cursor visibility
    pygame.mouse.set_visible(0)

    #main game loop
    while True:
        for event in pygame.event.get():
            if event.type == QUIT:
                pygame.quit()
                sys.exit()

        drawArena()
        drawPaddle(robot)
        drawAI(ai)
        drawBall(ball)

        ball = moveBall(ball,ballDirX,ballDirY)
        ballDirX,ballDirY = checkEdgeCollision(ball,ballDirX,ballDirY)

        robotScore,aiScore = checkPointScored(ball, robotScore,aiScore)


        ballDirX = ballDirX * checkHitBall(ball, robot,ai,ballDirX)


        robot = bot(ball,ballDirX,robot)

        observe(ball,ai,ballDirX,robot)
        ai = react(ai,ball,ballDirX)
        printMemory()




        displayScore(robotScore,aiScore)

        pygame.display.update()
        FPSCLOCK.tick(FPS)
        #gameOver(robotScore)






if __name__ == '__main__':
    main()

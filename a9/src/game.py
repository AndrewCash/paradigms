# Andrew Cash
# Programming Paradigms
# Fall 2018
# Assignment 9

import pygame
import time
import random

from pygame.locals import*
from time import sleep


#   _____ ____  ____   ____  ______    ___
#  / ___/|    \|    \ l    j|      T  /  _]
# (   \_ |  o  )  D  ) |  T |      | /  [_
#  \__  T|   _/|    /  |  | l_j  l_jY    _]
#  /  \ ||  |  |    \  |  |   |  |  |   [_
#  \    ||  |  |  .  Y j  l   |  |  |     T
#   \___jl__j  l__j\_j|____j  l__j  l_____j



class Sprite():
    def __init__(self, _type, _x, _y, _w, _h, update_method):
        self.type = _type
        self.x = _x
        self.y = _y
        self.w = _w
        self.h = _h
        self.vert_vel = 0
        self.marioFrame = 0
        self.coinCounter = 0
        self.hittingBottom = False

        self.update = update_method

        if (self.type == "Coin"):
            self.horz_vel = random.randint(-10, 10)
            self.vert_vel = random.randint(0, 5)

    def marioUpdate(self):
        #print("Mario Update!")

        # Update gravity
        self.vert_vel += 1.2
        self.y += self.vert_vel

        # Update animation
        if (self.x != self.prev_x):
            self.marioFrame = ((self.marioFrame + 1) % 5)


    def jump(self):
        #print("Jump")
        self.jumpCounter += 1
        if (self.jumpCounter < 5):
            self.vert_vel -= 5

    def goRight(self):
        self.x += 10

    def goLeft(self):
        self.x -= 10

    def brickUpdate(self):
        #print("Brick Update")
        pass

    def coinBlockUpdate(self):
        pass

    def coinUpdate(self):
        self.vert_vel += 1.2
        self.y += self.vert_vel
        self.x += self.horz_vel



#  ___ ___   ___   ___      ___  _
# |   T   T /   \ |   \    /  _]| T
# | _   _ |Y     Y|    \  /  [_ | |
# |  \_/  ||  O  ||  D  YY    _]| l___
# |   |   ||     ||     ||   [_ |     T
# |   |   |l     !|     ||     T|     |
# l___j___j \___/ l_____jl_____jl_____j


class Model():
    def __init__(self):
        self.sprites = []

        self.mario = Sprite("Mario", 50, 50, 60, 95, Sprite.marioUpdate)
        self.brick0 = Sprite("Brick", 20, 400, 1000, 50, Sprite.brickUpdate)
        self.brick1 = Sprite("Brick", 600, 350, 50, 50, Sprite.brickUpdate)
        self.coinBlock = Sprite("CoinBlock", 250, 100, 89, 83, Sprite.coinBlockUpdate)
        self.sprites.append(self.mario)
        self.sprites.append(self.brick0)
        self.sprites.append(self.brick1)
        self.sprites.append(self.coinBlock)

        self.dest_x = 0
        self.dest_y = 0

        self.scrollPos = 0


    def update(self):
        for i in self.sprites:
            #print(i.type)
            i.update(i)
            if (i.type != "Mario"):
                if (i.type == "Coin"):
                    pass
                if (self.isColliding(self.mario, i)):
                    self.getOut(self.sprites[0], i)
            if (i.type == "Mario"): # fyi this is here becasue mario doesnt have access tothe model and marioRect is stored in the model
                self.marioRect.left = i.x - self.scrollPos
                self.marioRect.top = i.y
            if (i.type == "CoinBlock"):
                self.coinBlockRect.left = i.x - self.scrollPos
                self.coinBlockRect.top = i.y
            if (i.type == "Coin"):
                self.coinRect.left = i.x - self.scrollPos
                self.coinRect.top = i.y

            self.backgroundRect.left = -(self.scrollPos / 2)


        self.scrollPos = self.mario.x - 20

    def set_dest(self, pos):
        self.dest_x = pos[0]
        self.dest_y = pos[1]

    def rememberPreviousPosition(self):
        self.mario.prev_x = self.mario.x
        self.mario.prev_y = self.mario.y

    def isColliding(self, mario, sprite):
        if (sprite.type == "Coin"):
            return

        if ((mario.x + mario.w) <= sprite.x):
            # print("Coming from right")
            return False
        elif (mario.x >= (sprite.x + sprite.w)):
            # print("Coming from left");
            return False
        elif((mario.y + mario.h) <= sprite.y):
            # print("Coming from top");
            # Assume down is positive
            return False
        elif(mario.y >= (sprite.y + sprite.h)):
            # print("Coming from bottom");
            # Assume down is positive
            return False
        else:
            # print("Colliding with object");
            return True

    def getOut(self, mario, sprite):
        # M left side hits B right side
        if (mario.x <= (sprite.x + sprite.w) and mario.prev_x > (sprite.x + sprite.w)):
            # print("Hitting Right")
            self.mario.x += 10
            return

        # M right side hits B left side
        elif ((mario.x + mario.w) >= sprite.x and (mario.prev_x + mario.w) < sprite.x):
            # print("Hitting Left")
            self.mario.x += -10
            return

        # M top hits B bottom
        elif (mario.y <= (sprite.y + sprite.h) and mario.prev_y >= (sprite.y + sprite.h)):
            # print("Hitting Bottom")
            self.mario.y = sprite.y + sprite.h + 1
            self.mario.vert_vel = 1
            # a.onObject = true;

            if (sprite.type == "CoinBlock"):
                sprite.hittingBottom = True

                if (sprite.coinCounter < 5):
                    s = Sprite("Coin", (sprite.x + (sprite.w / 2)), sprite.y, 75, 75, Sprite.coinUpdate)
                    self.sprites.append(s)
                    sprite.coinCounter += 1
                    sprite.hittingBottom = False
                    print(sprite.coinCounter)
            return

        # M bottom hits B top
        elif ((mario.y + mario.h) >= sprite.y and (mario.prev_y + mario.h) >= sprite.y):
            #print("Hitting Top")
            self.mario.y = sprite.y - self.mario.h + 1  # y + h = _y
            self.mario.vert_vel = 0
            self.mario.jumpCounter = 0
            return


#  __ __  ____    ___ __    __
# |  T  |l    j  /  _]  T__T  T
# |  |  | |  T  /  [_|  |  |  |
# |  |  | |  | Y    _]  |  |  |
# l  :  ! |  | |   [_l  `  '  !
#  \   /  j  l |     T\      /
#   \_/  |____jl_____j \_/\_/



class View():
    def __init__(self, model):
        screen_size = (800, 600)
        self.screen = pygame.display.set_mode(screen_size, 32)
        self.model = model

        self.background_image = pygame.image.load("images/background.jpg")
        self.mario_image = pygame.image.load("images/mario1.png")
        self.coinBlock_image = pygame.image.load("images/block1.png")
        self.coinBlockEmpty_image = pygame.image.load("images/block2.png")
        self.coin_image = pygame.image.load("images/coin.png")

        self.model.backgroundRect = self.background_image.get_rect()
        self.model.marioRect = self.mario_image.get_rect()
        self.model.coinBlockRect = self.coinBlock_image.get_rect()
        self.model.coinRect = self.coin_image.get_rect()

    def update(self):
        #print (self.model.mario.marioFrame)

        self.screen.fill([0, 200, 100])

        self.screen.blit(self.background_image, self.model.backgroundRect)

        for i in self.model.sprites:
            if i.type == "Brick":
                brickColor = (160, 68, 22)
                pygame.draw.rect(self.screen, brickColor, (i.x - self.model.scrollPos, i.y, i.w, i.h), 0)
            elif i.type == "Mario":
                self.screen.blit(self.mario_image, self.model.marioRect)
            elif i.type == "CoinBlock":
                if (i.coinCounter < 5):
                    self.screen.blit(self.coinBlock_image, self.model.coinBlockRect)
                else:
                    self.screen.blit(self.coinBlockEmpty_image, self.model.coinBlockRect)
            elif i.type == "Coin":
                self.screen.blit(self.coin_image, self.model.coinRect)

        # Update the full display Surface to the screen
        pygame.display.flip()

#     __   ___   ____   ______  ____    ___   _      _        ___  ____
#    /  ] /   \ |    \ |      T|    \  /   \ | T    | T      /  _]|    \
#   /  / Y     Y|  _  Y|      ||  D  )Y     Y| |    | |     /  [_ |  D  )
#  /  /  |  O  ||  |  |l_j  l_j|    / |  O  || l___ | l___ Y    _]|    /
# /   \_ |     ||  |  |  |  |  |    \ |     ||     T|     T|   [_ |    \
# \     |l     !|  |  |  |  |  |  .  Yl     !|     ||     ||     T|  .  Y
#  \____j \___/ l__j__j  l__j  l__j\_j \___/ l_____jl_____jl_____jl__j\_j


class Controller():
    def __init__(self, model):
        self.model = model
        self.keep_going = True

    def update(self):
        self.model.rememberPreviousPosition()

        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    self.keep_going = False
            elif event.type == pygame.MOUSEBUTTONUP:
                self.model.set_dest(pygame.mouse.get_pos())
        keys = pygame.key.get_pressed()
        if keys[K_LEFT]:
            self.model.sprites[0].goLeft()
        if keys[K_RIGHT]:
            self.model.sprites[0].goRight()
        if keys[K_UP] or keys[K_SPACE]:
            self.model.sprites[0].jump()

#  ___ ___   ____  ____  ____
# |   T   T /    Tl    j|    \
# | _   _ |Y  o  | |  T |  _  Y
# |  \_/  ||     | |  | |  |  |
# |   |   ||  _  | |  | |  |  |
# |   |   ||  |  | j  l |  |  |
# l___j___jl__j__j|____jl__j__j

print("Use the arrow keys to move. Press Esc to quit.")
pygame.init()
m = Model()
v = View(m)
c = Controller(m)
while c.keep_going:
    c.update()
    m.update()
    v.update()
    sleep(0.04)
print("Goodbye")


# ASCII art from http://patorjk.com/software/taag/#p=display&f=Crawford&t=Sprite

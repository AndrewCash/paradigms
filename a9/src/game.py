# Andrew Cash
# Programming Paradigms
# Fall 2018
# Assignment 9

import pygame
import time

from pygame.locals import*
from time import sleep

#   _____ ____  ____   ____  ______    ___
#  / ___/|    \|    \ l    j|      T  /  _]
# (   \_ |  o  )  D  ) |  T |      | /  [_
#  \__  T|   _/|    /  |  | l_j  l_jY    _]
#  /  \ ||  |  |    \  |  |   |  |  |   [_
#  \    ||  |  |  .  Y j  l   |  |  |     T
#   \___jl__j  l__j\_j|____j  l__j  l_____j
#


class Sprite():
    def __init__(self, _type, _x, _y, _w, _h, update_method):
        self.type = _type
        self.x = _x
        self.y = _y
        self.w = _w
        self.h = _h
        self.vert_vel = 0

        self.update = update_method

    def marioUpdate(self):
        print("Mario Update!")

        # Update gravity
        self.vert_vel += 1.2;
        self.y += self.vert_vel;

    def marioJump():
        print("Jump")

    def goRight():
        self.x += 10

    def goLeft():
        self.x -= 10

    def brickUpdate(self):
        print("Brick Update")
        pass


#  ___ ___   ___   ___      ___  _
# |   T   T /   \ |   \    /  _]| T
# | _   _ |Y     Y|    \  /  [_ | |
# |  \_/  ||  O  ||  D  YY    _]| l___
# |   |   ||     ||     ||   [_ |     T
# |   |   |l     !|     ||     T|     |
# l___j___j \___/ l_____jl_____jl_____j
#

class Model():
    def __init__(self):
        self.sprites = []

        mario = Sprite("Mario", 50, 50, 60, 95, Sprite.marioUpdate)
        brick = Sprite("Brick", 20, 400, 1000, 50, Sprite.brickUpdate)
        self.sprites.append(mario)
        self.sprites.append(brick)

        self.dest_x = 0
        self.dest_y = 0

    def update(self):
        for i in self.sprites:
            i.update(i)


        if self.rect.left < self.dest_x:
            self.rect.left += 10
        if self.rect.left > self.dest_x:
            self.rect.left -= 10
        if self.rect.top < self.dest_y:
            self.rect.top += 10
        if self.rect.top > self.dest_y:
            self.rect.top -= 10

    def set_dest(self, pos):
        self.dest_x = pos[0]
        self.dest_y = pos[1]

#  __ __  ____    ___ __    __
# |  T  |l    j  /  _]  T__T  T
# |  |  | |  T  /  [_|  |  |  |
# |  |  | |  | Y    _]  |  |  |
# l  :  ! |  | |   [_l  `  '  !
#  \   /  j  l |     T\      /
#   \_/  |____jl_____j \_/\_/
#

class View():
    def __init__(self, model):
        screen_size = (800,600)
        self.screen = pygame.display.set_mode(screen_size, 32)
        self.mario_image = pygame.image.load("images/mario1.png")
        self.model = model
        self.model.rect = self.mario_image.get_rect()

    def update(self):
        self.screen.fill([0,200,100])
        self.screen.blit(self.mario_image, self.model.rect)
        pygame.display.flip()

#     __   ___   ____   ______  ____    ___   _      _        ___  ____
#    /  ] /   \ |    \ |      T|    \  /   \ | T    | T      /  _]|    \
#   /  / Y     Y|  _  Y|      ||  D  )Y     Y| |    | |     /  [_ |  D  )
#  /  /  |  O  ||  |  |l_j  l_j|    / |  O  || l___ | l___ Y    _]|    /
# /   \_ |     ||  |  |  |  |  |    \ |     ||     T|     T|   [_ |    \
# \     |l     !|  |  |  |  |  |  .  Yl     !|     ||     ||     T|  .  Y
#  \____j \___/ l__j__j  l__j  l__j\_j \___/ l_____jl_____jl_____jl__j\_j
#

class Controller():
    def __init__(self, model):
        self.model = model
        self.keep_going = True

    def update(self):
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
            self.model.mario.goLeft()
        if keys[K_RIGHT]:
            self.model.mario.goRight()
        if keys[K_UP]:
            self.model.mario.jump()
        # if keys[K_DOWN]:
        #     self.model.dest_y += 1

#  ___ ___   ____  ____  ____
# |   T   T /    Tl    j|    \
# | _   _ |Y  o  | |  T |  _  Y
# |  \_/  ||     | |  | |  |  |
# |   |   ||  _  | |  | |  |  |
# |   |   ||  |  | j  l |  |  |
# l___j___jl__j__j|____jl__j__j
#

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

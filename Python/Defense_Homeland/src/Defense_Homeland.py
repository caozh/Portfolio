""" Zhihao_Final_Project.py 
    A combat game
    Use arrow key to move character
    Press SPACE to attack
    
    Author:      Zhihao Cao
    Last Modify: 2014/12/11
"""

import pygame,math,random
pygame.init()

#direction constants
EAST = 0
NORTHEAST = 1
NORTH = 2
NORTHWEST = 3
WEST = 4
SOUTHWEST = 5
SOUTH = 6
SOUTHEAST = 7



class Character(pygame.sprite.Sprite):
    
    def __init__(self, screen):
        self.screen = screen
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load("character/stopped e0000.bmp")
        self.image = self.image.convert()
        tranColor = self.image.get_at((1, 1))
        self.image.set_colorkey(tranColor)
        self.rect = self.image.get_rect()
        self.rect.center = (320, 240)
        
        #stopped  0
        #walking  1
        #attack   2
        #been hit 3
        #die      4
        self.state = 0
        self.movable = True
        self.attackLock = False
        self.gameover = False
        self.stoppedList = []
        self.walkingList     = []
        self.attackList  = []
        self.beenHitList = []
        self.dieList     = []
        self.loadPics()
        
        self.dir = EAST
        self.frame = 0
        self.delay = 1
        self.pause = self.delay
        self.speed = 7     
        
        if not pygame.mixer:
            print "problem with sound"
        else:
            pygame.mixer.init()
            self.sndBeenHit = pygame.mixer.Sound("cBeenHit.wav")
            self.sndAttack = pygame.mixer.Sound("cAttack.wav")
            self.sndDie = pygame.mixer.Sound("cDie.ogg")

    
    def checkBounds(self):
        if self.rect.centerx > self.screen.get_width()-20:
            self.rect.centerx = self.screen.get_width()-20
        if self.rect.centerx < 20:
            self.rect.centerx = 20
        if self.rect.centery > self.screen.get_height()-30:
            self.rect.centery = self.screen.get_height()-30
        if self.rect.centery < 90:
            self.rect.centery = 90

    def loadPics(self):
       stoppedBase = [
            "character/stopped e000",
            "character/stopped ne000",
            "character/stopped n000",
            "character/stopped nw000",
            "character/stopped w000",
            "character/stopped sw000",
            "character/stopped s000",
            "character/stopped se000"
       ]
       walkingBase = [
            "character/walking e000",
            "character/walking ne000",
            "character/walking n000",
            "character/walking nw000",
            "character/walking w000",
            "character/walking sw000",
            "character/walking s000",
            "character/walking se000"
        ]
       attackBase = [
            "character/attack e000",
            "character/attack ne000",
            "character/attack n000",
            "character/attack nw000",
            "character/attack w000",
            "character/attack sw000",
            "character/attack s000",
            "character/attack se000"
        ]
       beenHitBase = [
            "character/been hit e000",
            "character/been hit ne000",
            "character/been hit n000",
            "character/been hit nw000",
            "character/been hit w000",
            "character/been hit sw000",
            "character/been hit s000",
            "character/been hit se000"
        ]
       dieBase = [
            "character/tipping over e000",
            "character/tipping over ne000",
            "character/tipping over n000",
            "character/tipping over nw000",
            "character/tipping over w000",
            "character/tipping over sw000",
            "character/tipping over s000",
            "character/tipping over se000"
        ] 
       #stopped
       for dir in range(8):
            tempFile = stoppedBase[dir]
            imgName = "%s%d.bmp" % (tempFile, 0)
            tmpImg = pygame.image.load(imgName)
            tmpImg.convert()
            tranColor = tmpImg.get_at((0, 0))
            tmpImg.set_colorkey(tranColor)
            self.stoppedList.append(tmpImg)
            
       for dir in range(8):
            tempList = []
            tempFile = walkingBase[dir]
            for frame in range(8):
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.walkingList.append(tempList)
       
       for dir in range(8):
            tempList = []
            tempFile = attackBase[dir]
            for frame in range(13):
                if frame == 10:
                    tempFile = tempFile[:-1]
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.attackList.append(tempList)
       
       for dir in range(8):
            tempList = []
            tempFile = beenHitBase[dir]
            for frame in range(9):
                if frame == 10:
                    tempFile = tempFile[:-1]
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.beenHitList.append(tempList)
       
       for dir in range(8):
            tempList = []
            tempFile = dieBase[dir]
            for frame in range(11):
                if frame == 10:
                    tempFile = tempFile[:-1]
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.dieList.append(tempList)
       
       
    def update(self):
        if self.movable is True:
            self.checkKeys()
        self.animation()
        self.checkBounds()
        
    
    def animation(self):
        self.pause -= 1
        if self.pause <= 0:
            self.pause = self.delay
            
            #stopped
            if self.state == 0:
                self.image = self.stoppedList[self.dir]
                    
            #walking
            if self.state == 1:
                self.frame += 1
                if self.frame > 7:
                    self.frame = 0
                self.image = self.walkingList[self.dir][self.frame]
            #attack
            if self.state == 2:
                self.movable = False
                self.frame += 1
                if self.frame < 13:
                    self.image = self.attackList[self.dir][self.frame]
                else:
                    self.attackLock = False
                    self.state = 0
                    self.movable = True
            #been hit
            if self.state == 3:
                self.movable = False
                self.frame += 1
                if self.frame < 9:
                    self.image = self.beenHitList[self.dir][self.frame]
                else:
                    self.state = 0    
                    self.movable = True
            #die
            if self.state == 4:
                self.movable = False
                self.frame += 1
                if self.frame < 11:
                    self.image = self.dieList[self.dir][self.frame]
                else:
                    pygame.time.wait(2000)
                    self.gameover = True
                    
    
    def checkKeys(self):
        keys = pygame.key.get_pressed()
        
        if keys[pygame.K_LEFT]:
            self.rect.centerx -= self.speed
            self.dir = 4
            self.state = 1
            
        else:
            self.state = 0
        #    print "No K_LEFT Key"
        if keys[pygame.K_RIGHT]:
            self.rect.centerx += self.speed
            self.dir = 0
            self.state = 1
            
        if keys[pygame.K_UP]:
            self.rect.centery -= self.speed
            self.dir = 2
            self.state = 1
            
        if keys[pygame.K_DOWN]:
            self.rect.centery += self.speed
            self.dir = 6
            self.state = 1
            
        if keys[pygame.K_LEFT] and keys[pygame.K_UP]:
            distance = self.speed - math.sqrt(self.speed*self.speed/2) 
            self.rect.centery += distance
            self.rect.centerx += distance
            self.dir = 3
            self.state = 1
        if keys[pygame.K_LEFT] and keys[pygame.K_DOWN]:
            distance = self.speed - math.sqrt(self.speed*self.speed/2) 
            self.rect.centery -= distance
            self.rect.centerx += distance
            self.dir = 5
            self.state = 1
        if keys[pygame.K_RIGHT] and keys[pygame.K_UP]:
            distance = self.speed - math.sqrt(self.speed*self.speed/2) 
            self.rect.centery += distance
            self.rect.centerx -= distance
            self.dir = 1
            self.state = 1
        if keys[pygame.K_RIGHT] and keys[pygame.K_DOWN]:
            distance = self.speed - math.sqrt(self.speed*self.speed/2) 
            self.rect.centery -= distance
            self.rect.centerx -= distance
            self.dir = 7
            self.state = 1
                
                
        if keys[pygame.K_SPACE]:
            self.frame = 0
            self.state = 2
            self.attackLock = False
            self.sndAttack.play()
  
class Enemy(pygame.sprite.Sprite):    
    
    def __init__(self, screen):
        self.screen = screen
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load("enemy/stopped e0000.bmp")
        self.image = self.image.convert()
        tranColor = self.image.get_at((1, 1))
        self.image.set_colorkey(tranColor)
        self.rect = self.image.get_rect()
        self.rect.center = (520, 240)
        
        #stopped  0
        #walking  1
        #attack   2
        #been hit 3
        #die      4
        self.state = 1
        self.movable = True
        self.ifChase = True
        self.win     = False
        self.stoppedList = []
        self.walkingList = []
        self.attackList = []
        self.beenHitList = []
        self.dieList = []
        self.loadPics()
        
        self.dir = EAST
        self.frame = 0
        self.delay = 1
        self.pause = self.delay
        self.speed = 4      

        self.dxVals = (1,  .7,  0, -.7, -1, -.7, 0, .7)
        self.dyVals = (0, -.7, -1, -.7,  0,  .7, 1, .7)

        if not pygame.mixer:
            print "problem with sound"
        else:
            pygame.mixer.init()
            self.sndAttack = pygame.mixer.Sound("eAttack1.wav")
            self.sndDie = pygame.mixer.Sound("eDie.wav")
    def checkBounds(self):
        if self.rect.centerx > self.screen.get_width()-30:
            self.rect.centerx = self.screen.get_width()-30
        if self.rect.centerx < 30:
            self.rect.centerx = 30
        if self.rect.centery > self.screen.get_height()-30:
            self.rect.centery = self.screen.get_height()-30
        if self.rect.centery < 90:
            self.rect.centery = 90

    def loadPics(self):
       stoppedBase = [
            "enemy/stopped e000",
            "enemy/stopped ne000",
            "enemy/stopped n000",
            "enemy/stopped nw000",
            "enemy/stopped w000",
            "enemy/stopped sw000",
            "enemy/stopped s000",
            "enemy/stopped se000"
        ]
       walkingBase = [
            "enemy/walking e000",
            "enemy/walking ne000",
            "enemy/walking n000",
            "enemy/walking nw000",
            "enemy/walking w000",
            "enemy/walking sw000",
            "enemy/walking s000",
            "enemy/walking se000"
        ]
       attackBase = [
            "enemy/attack e000",
            "enemy/attack ne000",
            "enemy/attack n000",
            "enemy/attack nw000",
            "enemy/attack w000",
            "enemy/attack sw000",
            "enemy/attack s000",
            "enemy/attack se000"
        ]
       beenHitBase = [
            "enemy/been hit e000",
            "enemy/been hit ne000",
            "enemy/been hit n000",
            "enemy/been hit nw000",
            "enemy/been hit w000",
            "enemy/been hit sw000",
            "enemy/been hit s000",
            "enemy/been hit se000"
        ]
       dieBase = [
            "enemy/tipping over e000",
            "enemy/tipping over ne000",
            "enemy/tipping over n000",
            "enemy/tipping over nw000",
            "enemy/tipping over w000",
            "enemy/tipping over sw000",
            "enemy/tipping over s000",
            "enemy/tipping over se000"
        ]
       #stopped 
       for dir in range(8):
            tempFile = stoppedBase[dir]
            imgName = "%s%d.bmp" % (tempFile, 0)
            tmpImg = pygame.image.load(imgName)
            tmpImg.convert()
            tranColor = tmpImg.get_at((0, 0))
            tmpImg.set_colorkey(tranColor)
            self.stoppedList.append(tmpImg)
       #walking     
       for dir in range(8):
            tempList = []
            walkingTempFile = walkingBase[dir]
            for frame in range(8):
                imgName = "%s%d.bmp" % (walkingTempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.walkingList.append(tempList)
       #attack
       for dir in range(8):
            tempList = []
            tempFile = attackBase[dir]
            for frame in range(11):
                if frame == 10:
                    tempFile = tempFile[:-1]
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.attackList.append(tempList)
       #been hit
       for dir in range(8):
            tempList = []
            tempFile = beenHitBase[dir]
            for frame in range(9):
                if frame == 10:
                    tempFile = tempFile[:-1]
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.beenHitList.append(tempList)
       #die     
       for dir in range(8):
            tempList = []
            tempFile = dieBase[dir]
            for frame in range(11):
                if frame == 10:
                    tempFile = tempFile[:-1]
                imgName = "%s%d.bmp" % (tempFile, frame)
                tmpImg = pygame.image.load(imgName)
                tmpImg.convert()
                tranColor = tmpImg.get_at((0, 0))
                tmpImg.set_colorkey(tranColor)
                tempList.append(tmpImg)
            self.dieList.append(tempList)
       
       
       
    def update(self):
        self.animation()
        
        if self.movable is True:
            if self.ifChase is True:
                self.calcVector()
                self.rect.centerx += self.dx
                self.rect.centery += self.dy
        
        self.checkBounds()
        
    def calcVector(self):
        self.dx = self.dxVals[self.dir]
        self.dy = self.dyVals[self.dir]
        self.dx *= self.speed  
        self.dy *= self.speed
        
    def animation(self):
        self.pause -= 1
        if self.pause <= 0:
            self.pause = self.delay
            #stopped
            if self.state == 0:
                self.image = self.stoppedList[self.dir]
            #walking
            if self.state == 1:
                self.frame += 1
                if self.frame > 7:
                    self.frame = 0
                
                self.image = self.walkingList[self.dir][self.frame]
            #attack
            if self.state == 2:
                self.movable = False
                self.frame += 1
                if self.frame < 11:
                    self.image = self.attackList[self.dir][self.frame]
                else:
                    self.state = 0
                    self.movable = True
            #been hit
            if self.state == 3:
                self.movable = False
                self.frame += 1
                if self.frame < 9:
                    self.image = self.beenHitList[self.dir][self.frame]
                else:
                    self.state = 0
                    self.movable = True
            #die
            if self.state == 4:
                self.movable = False
                self.frame += 1
                if self.frame < 11:
                    self.image = self.dieList[self.dir][self.frame]
                else:
                    pygame.time.wait(1000)
                    self.win = True
                                    
            
#character attack enemy             
def characterAttack(character,enemy,eHP,d):
    #get the coordinate of two sprites    
    cx = character.rect.centerx
    cy = character.rect.centery
    ex = enemy.rect.centerx
    ey = enemy.rect.centery
    #Character face detection 
    #first quadrant
    #cx-ex>0 and cy-ey<0 and(s or sw or w)
    #Second quadrant
    #cx-ex<0 and cy-ey<0 and(s or se or e)
    #Third quadrant
    #cx-ex<0 and cy-ey>0 and(n or ne or e)
    #Fourth quadrant
    #cx-ex>0 and cy-ey>0 and(n or nw or w)
            
    cHitFlag = False
    #when character in attact state and distance within 70 
    if character.state == 2 and d < 70 and enemy.state != 3 and enemy.state != 4:
        if cx-ex >= 0 and cy-ey <= 0:
            if character.dir == 6 or character.dir == 5 or character.dir == 4:
                cHitFlag = True
        if cx-ex<0 and cy-ey<=0:
            if character.dir == 6 or character.dir == 7 or character.dir == 0:
                cHitFlag = True
        if cx-ex<0 and cy-ey>0:
            if character.dir == 2 or character.dir == 1 or character.dir == 0:
                cHitFlag = True
        if cx-ex>=0 and cy-ey>0:
            if character.dir == 2 or character.dir == 3 or character.dir == 4:
                cHitFlag = True
    #character attack can hit enemy
    if cHitFlag is True:
        character.sndBeenHit.play()
        eHP -= 10
        if eHP <= 0:
            enemy.frame = 0
            enemy.state = 4  
            enemy.sndDie.play()
        else:
            character.attackLock = True
            enemy.frame = 0
            enemy.state = 3
   
        
    return eHP        
 
#enemy attack character
def enemyAttack(character, enemy, cHP, d):
    if d < 70 and cHP > 0:
        #enemy attack reaction
        attachRate = random.random()
        if attachRate > 0.9:
            enemy.movable = False
            enemy.frame = 0
            enemy.state = 2
            enemy.sndAttack.play()
            cHP -= 20
            if cHP <= 0:
                character.sndDie.play()
                character.movable = False
                character.frame = 0
                character.state = 4
            else:
                character.movable = False
                character.frame = 0
                character.state = 3
    return cHP
 
#enemy chase character
def chase(character, enemy, d):
    if d < 70:
        enemy.ifChase = False
        enemy.state = 0
    else:
        enemy.ifChase = True
        enemy.state = 1
    #enemy chase reaction
    chaseRate = random.random()
    if chaseRate > 0.9:
        #enemy face detection 
        #first quadrant
        #ex-cx>0 and ey-cy<0 and sw 
        #Second quadrant
        #ex-cx<0 and ey-cy<0 and se 
        #Third quadrant
        #ex-cx<0 and ey-cy>0 and ne 
        #Fourth quadrant
        #ex-cx>0 and ey-cy>0 and nw 
        cx = character.rect.centerx
        cy = character.rect.centery
        ex = enemy.rect.centerx
        ey = enemy.rect.centery   
        if ex-cx>0 and ey-cy<0:
            enemy.dir = 5
        if ex-cx<0 and ey-cy<0:
            enemy.dir = 7
        if ex-cx<0 and ey-cy>0:
            enemy.dir = 1
        if ex-cx>0 and ey-cy>0:
            enemy.dir = 3
            
        if ex-cx==0 and ey-cy<0:
            enemy.dir = 6
        if ex-cx<0 and ey-cy==0:
            enemy.dir = 0
        if ex-cx==0 and ey-cy>0:
            enemy.dir = 2
        if ex-cx>0 and ey-cy==0:
            enemy.dir = 4

            

def game():
    healthbar = pygame.image.load("healthbar.png")
    health = pygame.image.load("health.png")
    bg = pygame.image.load("bg.png")
    screen = pygame.display.set_mode((640, 480))
    if not pygame.mixer:
        print "problem with sound"
    else:
        pygame.mixer.init()
        sndBg = pygame.mixer.Sound("bg.wav") 
    sndBg.play()        
    screen.blit(bg, (0, 0))
    
    enemy = Enemy(screen)
    character = Character(screen)
    allSprites = pygame.sprite.Group(enemy,character)
    #character and enemy health value
    cHP = 194
    eHP = 194
    
    
    clock = pygame.time.Clock()
    keepGoing = True
    while keepGoing:
        clock.tick(20)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                keepGoing = False
                
        #detection distance between two sprites
        cx = character.rect.centerx
        cy = character.rect.centery
        ex = enemy.rect.centerx
        ey = enemy.rect.centery
        dx = ex-cx
        dy = ey-cy
        d = math.sqrt(dx*dx + dy*dy)        
        #enemy chase character
        if enemy.movable is True:
            chase(character, enemy, d)
            #enemy attack character
            cHP = enemyAttack(character, enemy, cHP, d)
                
        if character.attackLock is False:
            #character attack enemy 
            eHP = characterAttack(character,enemy,eHP,d)    
   
        #when hp <= 0
        if cHP <= 0 and character.gameover is True:
            sndBg.stop() 
            keepGoing = False
            gameover()
        if eHP <= 0 and enemy.win is True:
            sndBg.stop() 
            keepGoing = False
            win()
            
        screen.blit(healthbar, (5,5)) 
        screen.blit(healthbar, (435,5)) 
        for health1 in range(cHP):
            screen.blit(health, (health1+8,8))
        for health1 in range(eHP):
            screen.blit(health, (health1+438,8))
        allSprites.clear(screen, bg)
        allSprites.update()
        allSprites.draw(screen)
        
        pygame.display.flip()

def gameover():
    screen = pygame.display.set_mode((640, 480))
    pygame.display.set_caption("Final Project")
    bg_rome = pygame.image.load("rome.jpg")
    screen.blit(bg_rome, (0, 0))
    if not pygame.mixer:
        print "problem with sound"
    else:
        pygame.mixer.init()
        sndGameover = pygame.mixer.Sound("gameover.ogg") 
    sndGameover.play()     
    insFont = pygame.font.Font("Skater Girls Rock.ttf", 40)
    insLabels = []
    instructions = (
    "                       ",
    "",
    "Unfortunately",
    "You are defeated by croco monster",
    "Your country is invaded",
    " ",
    "",
    "",    
    "",
    "Press SPACE to Restart"
    )
    for line in instructions:
        tempLabel = insFont.render(line, 1, (240,255,255))
        insLabels.append(tempLabel)
    
    
    keepGoing = True
    clock = pygame.time.Clock()
    pygame.mouse.set_visible(False)
    while keepGoing:
        clock.tick(30)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                keepGoing = False
                donePlaying = True
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    keepGoing = False
                    donePlaying = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    keepGoing = False
                    donePlaying = True
                    
        for i in range(len(insLabels)):
            screen.blit(insLabels[i], (10, 40*i))
            
        pygame.display.flip()

def win():
    screen = pygame.display.set_mode((640, 480))
    pygame.display.set_caption("Final Project")
    bg_rome = pygame.image.load("rome.jpg")
    screen.blit(bg_rome, (0, 0))
    if not pygame.mixer:
        print "problem with sound"
    else:
        pygame.mixer.init()
        sndWin = pygame.mixer.Sound("win.wav") 
    sndWin.play()      
    insFont = pygame.font.Font("Skater Girls Rock.ttf", 40)
    insLabels = []
    instructions = (
    "                       ",
    "",
    "Congratulations !",
    "You defeat croco monster",
    "Your save your country",
    "",
    "",
    "",    
    "",
    "Press SPACE to Restart"
    )
    for line in instructions:
        tempLabel = insFont.render(line, 1, (240,255,255))
        insLabels.append(tempLabel)
    
    
    keepGoing = True
    clock = pygame.time.Clock()
    pygame.mouse.set_visible(False)
    while keepGoing:
        clock.tick(30)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                keepGoing = False
                donePlaying = True
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    keepGoing = False
                    donePlaying = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    keepGoing = False
                    donePlaying = True
                    
        for i in range(len(insLabels)):
            screen.blit(insLabels[i], (10, 40*i))
            
        pygame.display.flip()
                    
def instructions():
    screen = pygame.display.set_mode((640, 480))
    pygame.display.set_caption("Final Project")
    bg_rome = pygame.image.load("rome.jpg")
    screen.blit(bg_rome, (0, 0))
    if not pygame.mixer:
        print "problem with sound"
    else:
        pygame.mixer.init()
        sndOpen = pygame.mixer.Sound("open.ogg") 
    sndOpen.play()          
    insFont = pygame.font.Font("Skater Girls Rock.ttf", 40)
    insLabels = []
    instructions = (
    "                        Defense Homeland",
    "",
    "Instructions",
    "Use arrow keys to control your character",
    "Press SPACE to attack",
    "Story",
    "You are a Great Warrior in Ancient Rome",    
    "Rome has been attacked by croco monster",
    "Now is the time to show your power",
    " ",
    "Press SPACE to start"
    )
    for line in instructions:
        tempLabel = insFont.render(line, 1, (240,255,255))
        insLabels.append(tempLabel)
    
    
    keepGoing = True
    clock = pygame.time.Clock()
    pygame.mouse.set_visible(False)
    while keepGoing:
        clock.tick(30)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                keepGoing = False
                donePlaying = True
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    sndOpen.stop()
                    keepGoing = False
                    donePlaying = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    keepGoing = False
                    donePlaying = True
                    
        for i in range(len(insLabels)):
            screen.blit(insLabels[i], (10, 40*i+5))
            
        pygame.display.flip()
        
def main():
    donePlaying = False
    while not donePlaying:
        donePlaying = instructions()
        if not donePlaying:
            game()
    
if __name__ == "__main__":
    main()

// ____  ____  ____  __  ____  ____
// / ___)(  _ \(  _ \(  )(_  _)(  __)
// \___ \ ) __/ )   / )(   )(   ) _)
// (____/(__)  (__\_)(__) (__) (____)

class Sprite {
    constructor(type, x, y, w, h, image_url, update_method, onclick_method) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        if (image_url != null) {
            this.image = new Image();
            this.image.src = image_url;
            this.image.width = w;
            this.image.height = h;
        }

        this.update = update_method;
        this.onclick = onclick_method;


        // Array of mario images
        if (type = "Mario") {
            this.marioImages = [];
            this.marioImages[0] = new Image();
            this.marioImages[0].src = 'images/mario1.png';

            this.marioImages[1] = new Image();
            this.marioImages[1].src = 'images/mario2.png';

            this.marioImages[2] = new Image();
            this.marioImages[2].src = 'images/mario3.png';

            this.marioImages[3] = new Image();
            this.marioImages[3].src = 'images/mario4.png';

            this.marioImages[4] = new Image();
            this.marioImages[4].src = 'images/mario5.png';
        }

    }

    set_destination(x, y) {
        this.dest_x = x;
        this.dest_y = y;
    }

    ignore_click(x, y) {}

    move(dx, dy) {
        this.dest_x = this.x + dx;
        this.dest_y = this.y + dy;
    }

    marioUpdate() {

        // Update gravity
        if (this.vert_vel === undefined)
            this.vert_vel = 0.0;
        this.vert_vel += 1.2;
        this.y += this.vert_vel;

        if (this.dest_x === undefined)
            return;

        // Move
        if (this.x < this.dest_x)
            this.x;
        else if (this.x > this.dest_x)
            this.x--;
        if (this.y < this.dest_y)
            this.y++;
        else if (this.y > this.dest_y)
            this.y--;

        // Iterate Mario animation
        if (this.marioFrame === undefined)
            this.marioFrame = 0.0;

		if (this.x != this.prev_x)
			this.image = this.marioImages[this.marioFrame++ % 5]

    }

    marioJump() {
        this.vert_vel -= 10;
    }

    brickUpdate() {}
}

// _  _   __  ____  ____  __
// ( \/ ) /  \(    \(  __)(  )
// / \/ \(  O )) D ( ) _) / (_/\
// \_)(_/ \__/(____/(____)\____/

class Model {

    constructor() {
        this.sprites = [];
        //this.sprites.push(new Sprite("lettuce", 50, 200, "images/lettuce.png", Sprite.prototype.brickUpdate, Sprite.prototype.ignore_click));
        this.mario = new Sprite("Mario", 50, 50, 60, 95, "images/mario1.png", Sprite.prototype.marioUpdate, Sprite.prototype.set_destination);
        this.brick = new Sprite("Brick", 20, 400, 1000, 50, null, Sprite.prototype.brickUpdate, Sprite.prototype.ignore_click);
        this.sprites.push(this.mario);
        this.sprites.push(this.brick);
    }

    update() {
        for (let i = 0; i < this.sprites.length; i++) {
            this.sprites[i].update();

            if (this.sprites[i].type != "Mario") {
                if (this.isColliding(this.mario, this.sprites[i]))
                    this.getOut(this.mario, this.sprites[i]);
            }
        }

		if (this.marioFrame === undefined)
            this.marioFrame = 0.0;
		this.scrollPos = this.mario.x - 200;
    }

    onclick(x, y) {
        for (let i = 0; i < this.sprites.length; i++) {
            this.sprites[i].onclick(x, y);
        }
    }

    move(dx, dy) {
        this.mario.move(dx, dy);
    }

    goRight() {
        this.mario.x += 10;
    }

    goLeft() {
        this.mario.x -= 10;
    }

    rememberPreviousPosition() {
        this.mario.prev_x = this.mario.x;
        this.mario.prev_y = this.mario.y;
    }

    isColliding(mario, sprite) {
        if ((mario.x + mario.w) <= sprite.x) {
            //System.out.println("Coming from right");
            return false;
        } else if (mario.x >= (sprite.x + sprite.w)) {
            //System.out.println("Coming from left");
            return false;
        } else if ((mario.y + mario.h) <= sprite.y) {
            //System.out.println("Coming from top");
            // Assume down is positive
            return false;
        } else if (mario.y >= (sprite.y + sprite.h)) {
            //System.out.println("Coming from bottom");
            // Assume down is positive
            return false;
        } else {
            //System.out.println("Colliding with object");
            return true;
        }
    }

    getOut(mario, sprite) {


        // M left side hits B right side
        if (mario.x <= (sprite.x + sprite.w) && mario.prev_x > (sprite.x + sprite.w)) {
            //System.out.println("Hitting Right");
            this.mario.x += 10;
            return;
        }

        // M right side hits B left side
        else if ((mario.x + mario.w) >= sprite.x && (mario.prev_x + mario.w) < sprite.x) {
            //System.out.println("Hitting Left");
            this.mario.x += -10;
            return;
        }

        // M top hits B bottom
        else if (mario.y <= (sprite.y + sprite.h) && mario.prev_y >= (sprite.y + sprite.h)) {
            //System.out.println("Hitting Bottom");
            this.mario.y = b.y + b.h + 1;
            this.mario.vert_vel = 0.0;
            //a.onObject = true;

            // if (b.am_I_a_CoinBlock()) {
            //     Coin c = new Coin(b.x, b.y);
            //     b.hittingBottom = true;
            //     if (b.coinCounter < 5)
            //         soundEffects.playCoinSound();
            // }

            return;
        }

        // M bottom hits B top
        else if ((mario.y + mario.h) >= sprite.y && (mario.prev_y + mario.h) >= sprite.y) {
            //System.out.println("Hitting Top");
            this.mario.y = sprite.y - this.mario.h + 1; // y + h = _y
            this.mario.vert_vel = 3.0;
            //this.mario.jumpCounter = 0;
        }

    }

}




// _  _  __  ____  _  _
// / )( \(  )(  __)/ )( \
// \ \/ / )(  ) _) \ /\ /
// \__/ (__)(____)(_/\_)

class View {
    constructor(model) {
        this.model = model;
        this.canvas = document.getElementById("myCanvas");
        this.turtle = new Image();
        this.turtle.src = "images/turtle.png";
    }

    update() {
        let ctx = this.canvas.getContext("2d");
        ctx.clearRect(0, 0, 1000, 500);
        for (let i = 0; i < this.model.sprites.length; i++) {
            let sprite = this.model.sprites[i];

			if (sprite.type == "Mario") {

			}
			else if (sprite.type == "Brick") {
                ctx.fillStyle = "#a04416";
                ctx.fillRect(sprite.x, sprite.y, sprite.w, sprite.h);
                ctx.stroke();
            } else
                ctx.drawImage(sprite.image, sprite.x, sprite.y);

        }
    }
}

// ___  __   __ _  ____  ____   __   __    __    ____  ____
// / __)/  \ (  ( \(_  _)(  _ \ /  \ (  )  (  )  (  __)(  _ \
// ( (__(  O )/    /  )(   )   /(  O )/ (_/\/ (_/\ ) _)  )   /
// \___)\__/ \_)__) (__) (__\_) \__/ \____/\____/(____)(__\_)

class Controller {
    constructor(model, view) {
        this.model = model;
        this.view = view;
        this.key_right = false;
        this.key_left = false;
        this.key_up = false;
        this.key_down = false;
        let self = this;
        view.canvas.addEventListener("click", function(event) {
            self.onClick(event);
        });
        document.addEventListener('keydown', function(event) {
            self.keyDown(event);
        }, false);
        document.addEventListener('keyup', function(event) {
            self.keyUp(event);
        }, false);
    }

    onClick(event) {
        this.model.onclick(event.pageX - this.view.canvas.offsetLeft, event.pageY - this.view.canvas.offsetTop);
    }

    keyDown(event) {
        if (event.keyCode == 39) this.key_right = true;
        else if (event.keyCode == 37) this.key_left = true;
        else if (event.keyCode == 38) this.key_up = true;
        else if (event.keyCode == 40) this.key_down = true;
        else if (event.keyCode == 32) this.key_space = true;

    }

    keyUp(event) {
        if (event.keyCode == 39) this.key_right = false;
        else if (event.keyCode == 37) this.key_left = false;
        else if (event.keyCode == 38) this.key_up = false;
        else if (event.keyCode == 40) this.key_down = false;
        else if (event.keyCode == 32) this.key_space = false;
    }

    update() {
        this.model.rememberPreviousPosition();

        let dx = 0;
        let dy = 0;

        if (this.key_right)
            this.model.goRight();
        if (this.key_left)
            this.model.goLeft();
        if (this.key_up || this.key_space)
            this.model.mario.marioJump();

        if (dx != 0 || dy != 0)
            this.model.move(dx, dy);
    }
}

// ___   __   _  _  ____
// / __) / _\ ( \/ )(  __)
// ( (_ \/    \/ \/ \ ) _)
// \___/\_/\_/\_)(_/(____)

class Game {
    constructor() {
        this.model = new Model();
        this.view = new View(this.model);
        this.controller = new Controller(this.model, this.view);
    }

    onTimer() {
        this.controller.update();
        this.model.update();
        this.view.update();
    }
}


let game = new Game();
let timer = setInterval(function() {
    game.onTimer();
}, 40);

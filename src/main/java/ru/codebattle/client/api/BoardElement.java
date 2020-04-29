package ru.codebattle.client.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoardElement {
    /// This is your Bomberman
    BOMBERMAN('☺'),             // this is what he usually looks like
    BOMB_BOMBERMAN('☻'),        // this is if he is sitting on own bomb
    DEAD_BOMBERMAN('Ѡ'),        // oops, your Bomberman is dead (don't worry, he will appear somewhere in next move)
    // you're getting -200 for each death

    /// this is other players Bombermans
    OTHER_BOMBERMAN('♥'),       // this is what other Bombermans looks like
    OTHER_BOMB_BOMBERMAN('♠'),  // this is if player just set the bomb
    OTHER_DEAD_BOMBERMAN('♣'),  // enemy corpse (it will disappear shortly, right on the next move)
    // if you've done it you'll get +1000

    /// the bombs
    BOMB_TIMER_5('5'),          // after bomberman set the bomb, the timer starts (5 tacts)
    BOMB_TIMER_4('4'),          // this will blow up after 4 tacts
    BOMB_TIMER_3('3'),          // this after 3
    BOMB_TIMER_2('2'),          // two
    BOMB_TIMER_1('1'),          // one
    BOOM('҉'),                  // Boom! this is what is bomb does, everything that is destroyable got destroyed


    /// walls
    WALL('☼'),                  // indestructible wall - it will not fall from bomb
    DESTROY_WALL('#'),          // this wall could be blowed up
    DESTROYED_WALL('H'),        // this is how broken wall looks like, it will dissapear on next move
    // if it's you did it - you'll get +10 points.

    /// meatchoppers
    MEAT_CHOPPER('&'),          // this guys runs over the board randomly and gets in the way all the time
    // if it will touch bomberman - it will die
    // you'd better kill this piece of ... meat, you'll get +100 point for it
    DEAD_MEAT_CHOPPER('x'),     // this is chopper corpse

    /// a void
    NONE(' ');                 // this is the only place where you can move your Bomberman


    private final char symbol;

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    public boolean isNextTickPassable() {
        return this == NONE;
    }

    public boolean isPassable() {
        return isNextTickPassable() || this == BOOM || this == DEAD_MEAT_CHOPPER || this == OTHER_DEAD_BOMBERMAN || this == DESTROYED_WALL;
    }

    public boolean isProtectFromExplosion() {
        return this == DESTROYED_WALL || this == WALL;
    }

    public static BoardElement valueOf(char ch) {
        for (BoardElement el : BoardElement.values()) {
            if (el.symbol == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element for " + ch);
    }
}

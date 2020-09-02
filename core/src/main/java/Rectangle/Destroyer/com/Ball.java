package Rectangle.Destroyer.com;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends PhysicsActor {

    private Circle prevCircle;
    private Circle currCircle;

    public Ball() {
        super();
    }

    public Circle getCircle(){
        return new Circle(getX() + getWidth()/2, getY() + getHeight()/2, getWidth()/2);
    }

    public boolean overlaps(Paddle paddle, boolean bounceOff){
        if (!Intersector.overlaps(this.getCircle(), paddle.getRectangle()))
            return false;
        if (bounceOff){
            float ballCenterX = this.getX() + this.getWidth()/2;
            float percent = (ballCenterX - paddle.getX()) / paddle.getWidth();
            // interpolate value between 150 and 30
            float bounceAngle = 150 - percent * 120;
            this.setVelocityAS(bounceAngle, this.getSpeed());
        }
        return true;
    }

    public void multVelocityX(float m){
        velocity.x *=m;
    }

    public void multVelocityY(float m){
        velocity.y *= m;
    }

    public void act(float dt){
        //store previous position before and after updating
        prevCircle = getCircle();
        super.act(dt);
        currCircle = getCircle();
    }

    public Vector2 getTop(Circle circle){
        return new Vector2(circle.x, circle.y + circle.radius);
    }

    public Vector2 getBottom(Circle circle){
        return new Vector2(circle.x, circle.y - circle.radius);
    }

    public Vector2 getLeft(Circle circle){
        return new Vector2(circle.x - circle.radius, circle.y);
    }

    public Vector2 getRight(Circle circle){
        return new Vector2(circle.x + circle.radius, circle.y);
    }

    public Vector2 getBottomLeft(Rectangle rectangle){
        return new Vector2(rectangle.getX(), rectangle.getY());
    }

    public Vector2 getBottomRight(Rectangle rectangle){
        return new Vector2(rectangle.getX() + rectangle.getWidth(), rectangle.getY());
    }

    public Vector2 getTopLeft(Rectangle rectangle){
        return new Vector2(rectangle.getX(), rectangle.getY() + rectangle.getHeight());
    }

    public Vector2 getTopRight(Rectangle rectangle){
        return new Vector2(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
    }

    public boolean overlaps(Brick brick, boolean bounceOff){
        if (!Intersector.overlaps(this.getCircle(), brick.getRectangle()))
            return false;

        if (bounceOff){
            Rectangle rectangle = brick.getRectangle();
            boolean sideHit = false;

            if (velocity.x > 0 && Intersector.intersectSegments(getRight(prevCircle), getRight(currCircle),
                            getTopLeft(rectangle), getBottomLeft(rectangle), null)){
                multVelocityX(-1);
                sideHit = true;
            }
            else if (velocity.x < 0 && Intersector.intersectSegments(getLeft(prevCircle), getLeft(currCircle),
                    getTopRight(rectangle), getBottomRight(rectangle), null)){
                multVelocityX(-1);
                sideHit = true;
            }

            if (velocity.y > 0 && Intersector.intersectSegments(getTop(prevCircle), getTop(currCircle),
                    getBottomLeft(rectangle), getBottomRight(rectangle), null)){
                multVelocityY(-1);
                sideHit = true;
            }
            else if (velocity.y < 0 && Intersector.intersectSegments(getBottom(prevCircle), getBottom(currCircle), getTopLeft(rectangle), getTopRight(rectangle), null)){
                multVelocityY(-1);
                sideHit = true;
            }

            if (!sideHit) //by process of elimination, corner was hit first
            {
                multVelocityX(-1);
                multVelocityY(-1);
            }
        }

        return true;
    }
}

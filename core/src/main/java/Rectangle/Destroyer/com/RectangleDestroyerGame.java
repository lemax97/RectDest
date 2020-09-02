package Rectangle.Destroyer.com;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class RectangleDestroyerGame extends BaseGame {
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
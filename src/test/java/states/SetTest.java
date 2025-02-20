package states;

import states.timer.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class SetTest {

    private Context context;

    @BeforeEach
    void setUp() {
        // reset the initial values of timer to avoid inferences between different consecutive tests
        context = new Context();
        context.currentState = IdleTimer.Instance(); // Starting at initial state
        context.right(); // Going to SetTimer state because we are testing it here...
        AbstractTimer.resetInitialValues();
    }

    @Test
    void testState() {
        // the state of the statechart following the initial state after the action right should be a SetTimer object
        assertSame(SetTimer.Instance(),context.currentState);
    }

    @Test
    void testUp() {
        // Testing that the up() event increases correctly the memory timer value by 5.
        // Testing that the up() event keeps us in the SetTimer state.
        assertEquals(0, SetTimer.getMemTimer());
        context.currentState.up();
        assertEquals(5, SetTimer.getMemTimer());
        assertSame(SetTimer.Instance(),context.currentState);
    }

    @Test
    void testRight() {
        // test whether the right() event brings us to the IdleTimer state
        assertSame(IdleTimer.Instance(), context.currentState.right());
    }

    @Test
    void testLeft() {
        // Testing that the left event() correctly resets the memory to 0.
        // Testing that the left event() keeps us in the SetTimerState.
        context.currentState.up(); // To change the memory to a different value than 0.
        assertNotEquals(0, SetTimer.getMemTimer());
        context.currentState.left();
        assertEquals(0, SetTimer.getMemTimer());
        assertSame(SetTimer.Instance(), context.currentState.left());
    }
}

package monday.ui;

import monday.constants.MessageConstants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for BubbleLayoutPolicy.
 * Tests layout decisions for GUI message bubbles.
 */
public class BubbleLayoutPolicyTest {

    @Test
    public void shouldUsePreformattedBubble_helpMessage_returnsTrue() {
        String helpMessage = MessageConstants.HELP_HEADER + MessageConstants.HELP_TODO;

        assertTrue(BubbleLayoutPolicy.shouldUsePreformattedBubble(helpMessage));
    }

    @Test
    public void shouldUsePreformattedBubble_regularMessage_returnsFalse() {
        assertFalse(BubbleLayoutPolicy.shouldUsePreformattedBubble("Fine. I've added this todo:"));
    }

    @Test
    public void resolveBubbleMaxWidthRatio_helpMessage_usesPreformattedWidth() {
        String helpMessage = MessageConstants.HELP_HEADER + MessageConstants.HELP_TODO;

        assertEquals(
                BubbleLayoutPolicy.PREFORMATTED_BUBBLE_MAX_WIDTH_RATIO,
                BubbleLayoutPolicy.resolveBubbleMaxWidthRatio(false, false, helpMessage)
        );
    }
}

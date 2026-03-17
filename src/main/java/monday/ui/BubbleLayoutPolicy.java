package monday.ui;

import monday.constants.MessageConstants;

/**
 * Encapsulates width and formatting rules for dialog bubbles.
 */
public final class BubbleLayoutPolicy {

    static final double USER_BUBBLE_MAX_WIDTH_RATIO = 0.70;
    static final double BOT_BUBBLE_MAX_WIDTH_RATIO = 0.78;
    static final double PREFORMATTED_BUBBLE_MAX_WIDTH_RATIO = 0.88;

    private BubbleLayoutPolicy() {
        // Utility class
    }

    static boolean shouldUsePreformattedBubble(String text) {
        return text != null && text.startsWith(MessageConstants.HELP_HEADER);
    }

    static double resolveBubbleMaxWidthRatio(boolean isUser, boolean isError, String text) {
        if (isUser) {
            return USER_BUBBLE_MAX_WIDTH_RATIO;
        }
        if (isError) {
            return BOT_BUBBLE_MAX_WIDTH_RATIO;
        }
        return shouldUsePreformattedBubble(text)
                ? PREFORMATTED_BUBBLE_MAX_WIDTH_RATIO
                : BOT_BUBBLE_MAX_WIDTH_RATIO;
    }
}

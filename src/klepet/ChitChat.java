package klepet;

public class ChitChat {

	public static ChatFrame chatFrame = new ChatFrame();
	public static Robot robot = new Robot(chatFrame);

	public static void main(String[] args) {
		robot.activate();
		chatFrame.pack();
		chatFrame.setVisible(true);
	}

}
package klepet;

public class ChitChat {

	public static void main(String[] args) {
		ChatFrame chatFrame = new ChatFrame();
		Robot robot = new Robot(chatFrame);
		robot.activate();
		chatFrame.pack();
		chatFrame.setVisible(true);
	}

}
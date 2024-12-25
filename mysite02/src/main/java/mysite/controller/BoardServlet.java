package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.DeleteAction;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.ModifyAction;
import mysite.controller.action.board.ModifyFormAction;
import mysite.controller.action.board.ReplyAction;
import mysite.controller.action.board.ReplyFormAction;
import mysite.controller.action.board.ViewAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"modify", new ModifyAction(),
			"modifyform", new ModifyFormAction(),
			"view", new ViewAction(),
			"write", new WriteAction(),
			"writeform", new WriteFormAction(),
			"delete", new DeleteAction(),
			"reply", new ReplyAction(),
			"replyform", new ReplyFormAction()
	);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}
}

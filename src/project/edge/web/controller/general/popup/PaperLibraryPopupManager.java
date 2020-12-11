package project.edge.web.controller.general.popup;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import project.edge.web.controller.archive.PaperLibraryController;
import project.edge.web.controller.common.SingleGridController;

@Component
public class PaperLibraryPopupManager extends AbstractPopupManager {

	@Resource
	private PaperLibraryController paperLibraryController;
	
	@Override
	protected SingleGridController<?, ?> getController() {
		// TODO Auto-generated method stub
		return this.paperLibraryController;
	}

}

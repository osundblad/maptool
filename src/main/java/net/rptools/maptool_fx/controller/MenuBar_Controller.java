/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool_fx.controller;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import net.rptools.maptool.client.AppActions;
import net.rptools.maptool.client.AppPreferences;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool_fx.MapTool;

public class MenuBar_Controller {
    @FXML private MenuItem openCampaignMenuItem;
    @FXML private CheckMenuItem connectionsWindowMenuItem;

    private static final Logger log = LogManager.getLogger(MenuBar_Controller.class);
    private MapTool_Controller mapTool_Controller;

    @FXML
    void initialize() {
        openCampaignMenuItem.disableProperty().bind(MapTool.getInstance().getServer().getConfig().isPersonalServer().not());
    }

    @FXML
    void openCampaign_OnAction(ActionEvent event) {
        if (MapTool.isCampaignDirty() && !MapTool.confirm("msg.confirm.loseChanges"))
            return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(I18N.getText("msg.title.loadCampaign"));
        fileChooser.setInitialDirectory(AppPreferences.getLoadDir());

        File campaignFile = fileChooser.showOpenDialog(null);
        AppActions.loadCampaign(campaignFile);

        // JFileChooser chooser = new CampaignPreviewFileChooser();
        // chooser.setDialogTitle(I18N.getText("msg.title.loadCampaign"));
        // chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // if (chooser.showOpenDialog(MapTool.getFrame()) == JFileChooser.APPROVE_OPTION) {
        // File campaignFile = chooser.getSelectedFile();
        // loadCampaign(campaignFile);
        // }
    }

    @FXML
    void startServer(ActionEvent event) {
        // TESTING
        MapTool.getInstance().getServer().getConfig().setPersonalServer(false);
    }

    @FXML
    void clientDisconnect(ActionEvent event) {
        // TESTING
        MapTool.getInstance().getServer().getConfig().setPersonalServer(true);
    }

    @FXML
    void windowCheckMenuItem_OnAction(ActionEvent event) {
        mapTool_Controller.showWindow((CheckMenuItem) event.getSource());
    }

    @FXML
    public void exit_OnAction() {
        MapTool.getInstance().close();
    }

    @FXML
    public void saveLayout_OnAction() {
        mapTool_Controller.saveLayout();
    }

    @FXML
    public void loadLayout_OnAction() {
        mapTool_Controller.loadLayout();
    }

    public void setParentControl(MapTool_Controller mapTool_Controller) {
        this.mapTool_Controller = mapTool_Controller;
    }
}
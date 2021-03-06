import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;


/**
 * @author Thomas Scruggs
 *
 * A class that creates the GUI for the subreddit view
 */

public class SubredditGUI implements SceneRender {

    private RedditController controller;

    /** Creates a scene object.
     *
     * @param subreddit is a Subreddit object.
     * @return a scene given the subreddit.
     */
    public static Scene getScene(Subreddit subreddit){

        HBox subredditTitle = SceneRender.addTitle(subreddit.getTitle());
        subredditTitle.setPadding(new Insets(20));
        HBox menuPane = addMenus(subreddit);
        VBox postsPane = addPosts(subreddit.getPostList());
        Text copyrightInfo = new Text("Reddit is a registered trademark of Reddit Inc.");
        Button nextPage = new Button("Next Page");
        nextPage.setOnAction(evt -> RedditController.requestSubredditPage(subreddit.getNextPageURL()));
        Button previousPage = new Button("Previous Page");
        previousPage.setOnAction(evt -> RedditController.requestBack());
        HBox bottomMenu = new HBox(previousPage, nextPage);
        bottomMenu.setSpacing(10);
        bottomMenu.setAlignment(Pos.CENTER);
        postsPane.getChildren().add(bottomMenu);

        ScrollPane postScroller = new ScrollPane(postsPane);
        postScroller.setFitToWidth(true);

        VBox header = new VBox();
        header.getChildren().addAll(subredditTitle, menuPane);

        Scene scene = new Scene(new BorderPane(postScroller, header, null, copyrightInfo, null), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }


    private static HBox addMenus(Subreddit subreddit) {
        HBox pane = new HBox();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setMinWidth(SCENE_WIDTH);

        Button back = new Button("Back");
        back.setOnAction(evt -> RedditController.requestBack());

        Button refresh = new Button("Refresh");
        //refresh.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/programmerhumor"));
        refresh.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/"+ subreddit.getTitle()));

        Button random = new Button("Random");
        random.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/random"));

        Button newSub = new Button("Search for a Subreddit");
        newSub.setOnAction(evt -> RedditController.requestSearchPage());

        pane.getChildren().addAll(back, refresh, random, newSub);

        return pane;
    }

    private static VBox addPosts(List<PostPreview> postList) {
        VBox postsVBox = new VBox();
        int numbPosts = postList.size();

        for (int i = 0; i < numbPosts; i ++){
            Node post = SceneRender.buildPostPreview(postList.get(i));
            postsVBox.getChildren().add(post);
        }
        return postsVBox;
    }

}

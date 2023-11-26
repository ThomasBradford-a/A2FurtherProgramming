package application;

public class Post {
	// Fields to represent post attributes
	private int postId;
	private String content;
	private String author;
	private int likes;
	private int shares;
	private String dateTime;
	private int mainPostId;

	// Constructor to initialize a Post object with the given attributes
	public Post(int postId, String content, String author, int likes, int shares, String dateTime, int mainPostId) {
		this.postId = postId;
		this.content = content;
		this.author = author;
		this.likes = likes;
		this.shares = shares;
		this.dateTime = dateTime;
		this.mainPostId = mainPostId;

	}

	// Getter methods to retrieve values of post attributes
	public int getPostId() {
		return postId;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public int getLikes() {
		return likes;
	}

	public int getShares() {
		return shares;
	}

	public String getDateTime() {
		return dateTime;
	}

	public int getMainPostId() {
		return mainPostId;
	}

	// Setter methods to update values of post attributes
	public void setPostId(int postId) {
		this.postId = postId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setMainPostId(int mainPostId) {
		this.mainPostId = mainPostId;
	}

	// Override toString method to provide a string representation of the Post
	// object
	@Override
	public String toString() {
		return "Post ID: " + postId + "\nContent: " + content + "\nAuthor: " + author + "\nLikes: " + likes
				+ "\nShares: " + shares + "\nDate and Time: " + dateTime + "\nMain Post ID: " + mainPostId;
	}
}

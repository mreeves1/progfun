package objsets

import common._
import TweetReader._

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int) {
  override def toString: String =
    "User: " + user + ", " +
    "Text: " + text + " [" + retweets + "]"
}

/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The eleemnts in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Tweet => Boolean): TweetSet

  /**
   * This is a helper method for `filter` that propagates the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
   def union(that: TweetSet): TweetSet

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def mostRetweeted: Tweet

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def descendingByRetweet: TweetList


  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit
}

class Empty extends TweetSet {
  def isEmpty: Boolean = {
    true
  }
  
  def filter(p: Tweet => Boolean): TweetSet = new Empty()

  
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = {
    acc // pass accumulator back up the stack?
  }

  def union(that: TweetSet): TweetSet = {
    that
  }
  
  def mostRetweeted: Tweet = {
    new Tweet("", "", 0)
    // throw new java.util.NoSuchElementException("Empty tweet set has no members ")
    
  }
  
  def descendingByRetweet: TweetList = {
    Nil
  }
  
  
  /**
   * The following methods are already implemented
   */

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = {
    // println(tweet.toString()+ " was included")
    new NonEmpty(tweet, new Empty, new Empty)
  }
  def remove(tweet: Tweet): TweetSet = this

  def foreach(f: Tweet => Unit): Unit = ()
}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {
  def isEmpty: Boolean = {
    false
  }
  
  def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, new Empty())
  
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = {
    if (p(elem)) {
      val newLeft1 = left.filterAcc(p, acc.incl(elem))
      right.filterAcc(p, newLeft1)
    } else {
      val newLeft2 = left.filterAcc(p, acc)
      right.filterAcc(p, newLeft2)
    }
  }
  
  def union(that: TweetSet): TweetSet = {
    // remove from this and add to that so empty returns that at end?
    right.union(left.union(that.incl(elem))) 
  }
  
  def mostRetweeted: Tweet = {
    val tmpMost1 = elem
    val tmpMost2 = left.mostRetweeted
    val tmpMost3 = right.mostRetweeted
    if (tmpMost1.retweets > tmpMost2.retweets && tmpMost1.retweets > tmpMost3.retweets) {
      tmpMost1
    } else if (tmpMost2.retweets > tmpMost3.retweets && tmpMost2.retweets > tmpMost1.retweets) {
      tmpMost2
    } else {
      tmpMost3
    }
  }
    
  def descendingByRetweet: TweetList = {
    val most1 = this.mostRetweeted;
    new Cons(most1,this.remove(most1).descendingByRetweet)
  }

  /**
   * The following methods are already implemented
   */

  def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  def incl(x: Tweet): TweetSet = {
    // println(x.toString()+ " was included")
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (elem.text < x.text) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }
}

trait TweetList {
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")
  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
}


object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  // lazy val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => tweet.text.contains(google))
  // lazy val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => tweet.retweets > 20)
  // lazy val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => tweet.text.contains("Carbon))
  lazy val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => chkWords(tweet.text, google))
  lazy val appleTweets: TweetSet = TweetReader.allTweets.filter(tweet => chkWords(tweet.text, apple))
  
  def chkWords(text: String, list: List[String]): Boolean = {
    for (word <- list) {
      if (text.contains(word)) {
        return true
      }
    }
    false
    
  }
  
  
  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
  lazy val trending: TweetList = googleTweets.union(appleTweets).descendingByRetweet
}

object Main extends App {
  val tweetSet1 = new Empty()
  val tweet1 = new Tweet("mike", "Hello Ocean", 5)
  val tweet2 = new Tweet("mike", "Hello Asteroid", 20)
  val tweet3 = new Tweet("mike", "Hello Trees", 30)
  
  val tweetSet2 = tweetSet1.incl(tweet1)
  val tweetSet3 = tweetSet2.incl(tweet2)
  val tweetSet4 = tweetSet3.incl(tweet3)
  
  // println("tweetSet4 results ******")
  // tweetSet4 foreach println

  val tweetSet5 = tweetSet4.filter(tweet => tweet.retweets > 9)
  // println("tweetSet5 results ******")
  // tweetSet5 foreach println
  // println(tweetSet4.mostRetweeted)
  // tweetSet4.descendingByRetweet foreach println
  // Print the trending tweets
  // GoogleVsApple.trending foreach println
  println("\nGoogle Tweets ****************************")
  GoogleVsApple.googleTweets.descendingByRetweet foreach println
  println("\nApple Tweets *****************************")
  GoogleVsApple.appleTweets.descendingByRetweet foreach println
  println("\nTrending *********************************")
  GoogleVsApple.trending foreach println
}

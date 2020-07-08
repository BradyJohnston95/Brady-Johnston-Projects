# Brady Johnston
# This is a simple program that demonstrates basic functions used with Tweepy to
# interact with the Twitter API to do things like Tweet and interact with Twitter
# as a whole. If you have any questions, email me at bradyjohnston95@gmail.com!

# I import the Tweepy library which I will use to interact with the Twitter API
import tweepy

# I got these keys by creating a Twitter developer account and a new project
consumer_key='WQt9ryT6TnKskKNJF7hyoNlGL'
consumer_secret_key='Ad8Lv4DOnX1rp493D1Q32fFgBp4xSw6XR1vxzVes4QShUA5KQG'
access_token='1277089246461952001-iqnmEhWFneXsxOGYZsFidOlZZIT96Z'
access_token_secret='0K8xppQTWcEyy9pTrX8NKWWiYqLl95opiJ5EFf2MJziU3'

# I use those keys to initialize the api class with the keys.
auth=tweepy.OAuthHandler(consumer_key,consumer_secret_key)
auth.set_access_token(access_token,access_token_secret)
api=tweepy.API(auth)

# I can use the update_status function with this string to post a Tweet.
tweet=('@JProjects34 This is a test to interact with mentions')
api.update_status(tweet)

# I gather mentions from my timeline and respond to the first mention.
# mention_id is used with update_status to make it a reply to that
# mention instead of its own Tweet
blah = api.mentions_timeline().
mention_id = blah[0].id_str
api.update_status('this is a response!', mention_id)

# In order to post a Tweet with media, I must first upload it with
# media_upload and then hold on to its id using the .media_id attribute.
# With that id, we can add it as an attribute to an update_status call.
picture = api.media_upload('corgi001.jpg')
id_list = [picture.media_id]
api.update_status('A picture of a Corgi', media_ids = id_list)

# I use update_profile to update my profile bio
api.update_profile(description = 'This is an account where I can experiment with the twitter API!')

# I use the search function with the 'Dogs' keyword to grab 4 Tweets from
# popular results that contain 'Dogs'. It returns a list, so I then print
# the text for each Tweet within the list.
tweet_list = api.search('Dogs', result_type = 'popular', count = '4')
for i in tweet_list:
    print(i.text + '\n')

# I use the search function with the 'Corgi' keyword to grab the 1st Tweet
# from a search within popular results and I then use the create_favorite
# function to like that Tweet.
corgi_tweet = api.search('Corgi', result_type = 'popular', count = '1')
api.create_favorite(corgi_tweet[0].id_str)

Design Patterns Used:

1. Model-View-Controller (MVC)
   Model Classes:
      - NewsStore
      - Article
      - Consumer
      - EmailClient

   View Classes:
      - MainView
      - ReportersView
      - SubscriberView
      - View (interface)

   Controller Classes:
      - MainController

   Notes:
      The MainController manages the application flow, invokes model logic,
      and switches between views. Each view invokes controller handlers on
      user action.


2. Observer Pattern
   Subject (Observable):
      - NewsStore

   Observer Interface:
      - Subscriber

   Concrete Observer:
      - Consumer

   Notes:
      NewsStore maintains a mapping from hashtags to lists of Subscribers.
      When a new Article is added, NewsStore notifies all Consumers
      subscribed to the matching hashtag.


3. Singleton Pattern
   Singleton Class:
      - NewsStore

   Notes:
      NewsStore enforces a single shared instance for storing articles and
      managing subscriptions.

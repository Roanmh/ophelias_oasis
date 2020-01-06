# Software Engineering Final

CLI reservation system for the fictional hotel 'Ophelia's Oasis'.

# Summary

The requirements mandated a number of components:
 - Reservation Business Logic
 - User Interface
 - Persistent Storage
 - Report Generation

I focused on the presistant storage implementation as well as organizing the
interfaces between the components that were designed by my teammates.
[Records.java](OpheliasOasis/src/opheliasoasis/Records.java) contains most of my
work on the persistent storage. I hope the desription and the commit mentioned
below speak to my organizational work.

# Persistent Storage

First and foremost, my team was looking for a dead simple solution. We
especially wanted to avoid requirements outside of default-available java
features. We also knew the performance was not a priority. With this in mind, I
chose to serialize a collection of the records into a local file for the next
run to unpack.

[Records.java](OpheliasOasis/src/opheliasoasis/Records.java)

# Interface Organization

An initial question we faced as a team was how to organize the object oriented
architecture. The most acute questions arose from generalization and
abstraction. I helped to lead the discussion and wrote the first drafts of some
of the classes' functions. We decided on an object oriented architecture for its
familiarity and its potential for work separation. This left the question of
what each class would be responsible for and what data it would be privy to. We
identified interface classes that would abstract things like the database and
reports from the business logic. We also identified persistence classes that
would hold the reservations and daily rates. Finally, we identified the main
class as a business domain class. This is wehre we implemented most of the
behaviors required by the specifications. This work, especially the class-stubs
may be more clear from [this
commit](https://github.com/Roanmh/eecs3350_team6/tree/ce6f4f8fa09e67ee016788a726af4db4017a7d78/OpheliasOasis/src/opheliasoasis)
(especially with git-blame).

Commit:
[ce6f4f8](https://github.com/Roanmh/eecs3350_team6/tree/ce6f4f8fa09e67ee016788a726af4db4017a7d78/OpheliasOasis/src/opheliasoasis)

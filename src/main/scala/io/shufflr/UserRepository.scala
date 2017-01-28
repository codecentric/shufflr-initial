/*
 * Copyright 2016 codecentric AG
 */

package io.shufflr

import java.util.concurrent.ConcurrentHashMap
import org.scalactic.{ Bad, Good, Or }
import scala.collection.JavaConverters

object UserRepository {

  final case class User(username: String, email: String)
  final case class UsernameTaken(username: String)
  final case class UsernameUnknown(username: String)
}

final class UserRepository(initialUsers: Map[String, UserRepository.User] = Map.empty) {
  import JavaConverters._
  import UserRepository._

  private val users = new ConcurrentHashMap[String, User](initialUsers.asJava)

  def getUsers: Set[User] =
    users.asScala.valuesIterator.to[Set]

  def getUser(username: String): Option[User] =
    Option(users.get(username)) // We all love `null`!

  def addUser(username: String, email: String): Or[User, UsernameTaken] = {
    val user = User(username, email)
    if (users.putIfAbsent(username, user) == null) Good(user) else Bad(UsernameTaken(username))
  }

  def removeUser(username: String): Or[User, UsernameUnknown] = {
    val user = users.remove(username)
    if (user != null) Good(user) else Bad(UsernameUnknown(username))
  }
}

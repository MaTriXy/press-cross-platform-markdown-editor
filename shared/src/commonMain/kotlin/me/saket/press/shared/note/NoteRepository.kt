package me.saket.press.shared.note

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import me.saket.press.data.shared.Note
import me.saket.press.shared.db.NoteId
import me.saket.press.shared.util.Optional

// Note to self: I'm no longer sure if having an abstraction over the Sql table
// is of any value. Letting Presenters access the table directly could be simpler.
interface NoteRepository {
  fun create(vararg insertNotes: InsertNote): Completable

  fun create(id: NoteId, content: String): Completable {
    return create(InsertNote(id, content))
  }
}

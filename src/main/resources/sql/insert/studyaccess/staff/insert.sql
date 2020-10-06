INSERT INTO
  studyaccess.staff (user_id, is_owner)
VALUES
  (?, ?)
RETURNING
  staff_id INTO ?

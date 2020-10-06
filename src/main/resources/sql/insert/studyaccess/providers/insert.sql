INSERT INTO
  studyaccess.providers (user_id, is_manager, dataset_id)
VALUES
  (?, ?, ?)
RETURNING
  provider_id INTO ?

enum Json:
  case JsonString(value: String)
  case JsonNumber(value: Int)
  case JsonBoolean(value: Boolean)
  case JsonNull
  case JsonArray(value: List[Json])
  case JsonObject(value: Map[String, Json])

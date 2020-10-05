
# Serializer and Deserializer Format

## XML vs Json vs Protobuf

| Format            | XML                                                                      | Json                                                           | Protobuf                                 |
| ----------------- | ------------------------------------------------------------------------ | -------------------------------------------------------------- | ---------------------------------------- |
| Briefness         | Really verbose                                                           | concise syntax with key:value                                  | binary format, the most concise of all   |
| Human Readability | Readable but a lot of tags                                               | easy to read                                                   | not human-readable                       |
| language support  | Highly supported with build library                                      | Highly supported with build library                            | support most language with library       |
| Data type support | flexible but need more information in the tag (see XML-RPC and Document) | support map, number, string, bool without any need of metadata | can define data structure like an entity |
| Performance       | fast but typically slower than Json                                      | fastest human-readable format                                  | very very fast format                    |

![benchmark format chart](../assets/Benchmark-format.png)

> regarding the result in Java, the best approach would be the Protocol Buffer to serialize and deserialize

### notes
XML is readable but harder to write with contract-first approach
JSON is easy to write but cannot make a strong contract and a way to generate code
Protobuf is not readable but the proto file are the most easy and human friendly among all and make it possible to define a strong contract

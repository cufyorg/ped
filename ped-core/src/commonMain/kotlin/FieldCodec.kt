/*
 *	Copyright 2023 cufy.org and meemer.com
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufy.ped

/* ============= ------------------ ============= */

/**
 * A codec specifically for the value of some field.
 * It stores two things, the name of the field, and
 * how to encode and decode its values.
 *
 * @param I the type of the decoded value.
 * @param O the type of the encoded value.
 * @author LSafer
 * @since 2.0.0
 */
interface FieldCodec<I, O> : Codec<I, O> {
    /**
     * The name of the field.
     */
    val name: String
}

/**
 * Create a new [FieldCodec] with the given [name]
 * and backed by the given [codec].
 */
fun <I, O> FieldCodec(name: String, codec: Codec<I, O>): FieldCodec<I, O> {
    return object : FieldCodec<I, O>, Codec<I, O> by codec {
        override val name = name
    }
}

/* ============= ------------------ ============= */

@PedMarker3
infix fun <I, O> FieldCodec<I, O>.defaultIn(defaultValue: I): FieldCodec<I, O> {
    val codec = this as Codec<I, O>
    return FieldCodec(name, codec defaultIn defaultValue)
}

@PedMarker3
infix fun <I, O> FieldCodec<I, O>.catchIn(block: (Throwable) -> I): FieldCodec<I, O> {
    val codec = this as Codec<I, O>
    return FieldCodec(name, codec catchIn block)
}

@PedMarker3
infix fun <I, O> FieldCodec<I, O>.defaultOut(defaultValue: O): FieldCodec<I, O> {
    val codec = this as Codec<I, O>
    return FieldCodec(name, codec defaultOut defaultValue)
}

@PedMarker3
infix fun <I, O> FieldCodec<I, O>.catchOut(block: (Throwable) -> O): FieldCodec<I, O> {
    val codec = this as Codec<I, O>
    return FieldCodec(name, codec catchOut block)
}

/* ============= ------------------ ============= */

/**
 * Return a string derived from this one with
 * its content tagged with the given language [tag].
 */
@PedMarker3
infix fun String.lang(tag: String): String {
    if (tag.isEmpty()) return this
    return "$this#$tag"
}

/**
 * Return a field codec derived from this one with
 * its name tagged with the given language [tag].
 */
@PedMarker3
infix fun <I, O> FieldCodec<I, O>.lang(tag: String): FieldCodec<I, O> {
    if (tag.isEmpty()) return this
    return FieldCodec("$name#$tag", this)
}

/**
 * Create a new field codec with the given [name]
 * and backed by [this] codec.
 */
@PedMarker3
infix fun <I, O> Codec<I, O>.at(name: String): FieldCodec<I, O> {
    return FieldCodec(name, this)
}

/**
 * Create a new field codec with the receiver name
 * and backed by the given [codec].
 *
 * Example:
 *
 * ```
 * object User {
 *      val Name = "name" be Codecs.String
 *      val Age = "age" be Codecs.Int64.Nullable
 * }
 * ```
 */
@PedMarker3
infix fun <I, O> String.be(codec: Codec<I, O>): FieldCodec<I, O> {
    return FieldCodec(this, codec)
}

/* ============= ------------------ ============= */

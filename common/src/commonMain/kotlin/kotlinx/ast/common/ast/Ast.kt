package kotlinx.ast.common.ast

import kotlinx.ast.common.AstChannel

interface Ast {
    val description: String
}

interface AstTerminal : Ast {
    val text: String

    companion object {
        operator fun <T> invoke(
            description: String,
            text: String,
            channel: AstChannel,
            line: Int,
            charPositionInLine: Int,
            startIndex: Int,
            stopIndex: Int
        ): AstTerminal {
            return DefaultAstTerminal(
                description, text, channel, TokenPositionInfo(
                    line,
                    charPositionInLine,
                    startIndex,
                    stopIndex
                )
            )
        }
    }
}

data class TokenPositionInfo(
    val line: Int,
    val charPositionInLine: Int,
    val startIndex: Int,
    val stopIndex: Int
)

data class DefaultAstTerminal(
    override val description: String,
    override val text: String,
    val channel: AstChannel,
    val tokenHolder: TokenPositionInfo
) : AstTerminal

interface AstNode : Ast {
    val children: List<Ast>

    companion object {
        operator fun <T> invoke(
            description: String,
            children: List<Ast>
        ): AstNode {
            return DefaultAstNode(description, children)
        }
    }
}

data class DefaultAstNode(
    override val description: String,
    override val children: List<Ast>
) : AstNode

interface AstGroup : Ast

package kotlinx.ast.grammar.kotlin.common.summary

import kotlinx.ast.common.ast.AstNode
import kotlinx.ast.common.klass.KlassAnnotation
import kotlinx.ast.common.klass.KlassDeclaration
import kotlinx.ast.common.klass.KlassIdentifier
import kotlinx.ast.common.map.TreeMapMapper
import kotlinx.ast.common.map.TreeMapResult

val annotationsMapper: TreeMapMapper = TreeMapMapper()
    .flattenNames("unescapedAnnotation")
    .filterChildren(
        flatten = true,
        childrenNamesToKeep = mapOf(
            "singleAnnotation" to setOf("unescapedAnnotation")
        )
    ).map("annotation") { node: AstNode ->
        treeMap(node.children, attachRawAst = attachRawAst).map { summary ->
            TreeMapResult.Continue(
                KlassAnnotation(
                    raw = attach(node),
                    identifier = summary.filterIsInstance<KlassIdentifier>(),
                    arguments = summary.filterIsInstance<KlassDeclaration>()
                )
            )
        }
    }

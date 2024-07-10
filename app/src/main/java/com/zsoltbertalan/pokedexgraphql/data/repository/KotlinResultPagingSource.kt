package com.zsoltbertalan.pokedexgraphql.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zsoltbertalan.pokedexgraphql.common.util.Outcome

const val PAGING_PAGE_SIZE = 20
const val PAGING_PREFETCH_DISTANCE = 5

/**
 * @param block A function that returns a Result in the form of a Pair of list and total results, while taking an
 * integer as a parameter. The parameter here is the page offset, where 0 means it start with the first item and 100
 * means it start with the 100th item. Elsewhere it could be the page number.
 */
fun <V : Any> createPager(
	block: suspend (Int) -> Outcome<Pair<List<V>, Int>>
): Pager<Int, V> = Pager(
	config = PagingConfig(pageSize = PAGING_PAGE_SIZE, prefetchDistance = PAGING_PREFETCH_DISTANCE),
	pagingSourceFactory = { KotlinResultPagingSource(block) }
)

class KotlinResultPagingSource<T : Any>(
	private val block: suspend (Int) -> Outcome<Pair<List<T>, Int>>
) : PagingSource<Int, T>() {

	override fun getRefreshKey(state: PagingState<Int, T>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGING_PAGE_SIZE)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGING_PAGE_SIZE)
		}
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
		val page = params.key ?: 0
		return try {
			val result = block(page)
			when {
				result.isOk -> LoadResult.Page(
					data = result.value.first,
					prevKey = if (page == 0) null else page - PAGING_PAGE_SIZE,
					nextKey = if (page > result.value.second - PAGING_PAGE_SIZE) null else page + PAGING_PAGE_SIZE
				)
				else -> throw Exception()
			}
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}
}

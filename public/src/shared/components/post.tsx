import React from 'react'
import { InstagramEmbed } from 'react-social-media-embed';

const Post = ({ posts, loading } : any) => {
    if (loading) {
        return (
            <h1>Loading.....</h1>
        )
    }
    return (
        <>
            {posts.map((post : string) => (
                <div className='list' key={post}>
                    <InstagramEmbed url={post} width={328} />
                </div>
            ))}
        </>
    )
}

export default Post
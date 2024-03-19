import axios from 'axios';
import * as React from 'react';
import {UserContextProvider, UserContextValue, UserContext} from "../shared/contexts/UserContext"
import Pagination from '../shared/components/pagination';
import Post from '../shared/components/post';
import Box from '@mui/material/Box/Box';
import Tabs from '@mui/material/Tabs/Tabs';
import Tab from '@mui/material/Tab/Tab';

const Posts = () => {
    interface TabPanelProps {
        children?: React.ReactNode;
        index: number;
        value: number;
      }

    const [currentPost, setCurrentPost] = React.useState("all");
    const [posts, setPosts] = React.useState<string[]>([]);
    const [loading, setLoading] = React.useState(false);
    const { isAuth } = React.useContext<UserContextValue>(UserContext);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [postsPerPage, setPostsPerPage] = React.useState(8);
    const [value, setValue] = React.useState(0);

    const fetchPosts = (async () => {
        setLoading(true)
        setCurrentPost("all")
        const response = await axios.get("/all-posts", {params: {token: localStorage.getItem("2w3e8oi9pjuthyf4")}});
        const data = await response.data.posts;
        setPosts(data);
        setLoading(false);
      });

      const fetchPostsByType = (async (type: string) => {
        setLoading(true)
        setCurrentPost(type)
        let requestType = ""
        console.log(type)
        if(type == "reels")
            requestType = "IG reel"
        else if(type == "carrousel")
            requestType = "IG carousel"
        else if(type == "images")
            requestType = "IG image"
        const response = await axios.get("/posts-by-type", {params: {token: localStorage.getItem("2w3e8oi9pjuthyf4"), type: requestType}});
        const data = await response.data.posts;
        console.log("DATA")
        console.log(data)
        setPosts(data);
        setLoading(false);
      });

      React.useEffect(() => {
          fetchPosts();
      }, []);

          // Get current posts
    const indexOfLastPost = currentPage * postsPerPage;
    const indexOfFirstPost = indexOfLastPost - postsPerPage;
    const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);

    // Change page
    const handlePagination = (pageNumber: number) => {
        setCurrentPage(pageNumber);
      };

      const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        console.log(newValue)
        setValue(newValue);
        if(newValue == 0 && currentPost != "all")
            fetchPosts()
        else if (newValue == 1 && currentPost != "reels")
            fetchPostsByType("reels")
        else if (newValue == 2 && currentPost != "carrousel")
            fetchPostsByType("carrousel")
        else if (newValue == 3 && currentPost != "images")
            fetchPostsByType("images")
      };

      function a11yProps(index: number) {
        return {
          id: `simple-tab-${index}`,
          'aria-controls': `simple-tabpanel-${index}`,
        };
      }

    return (<div>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
  <Tabs value={value} onChange={handleTabChange} aria-label="basic tabs example">
    <Tab label="All posts" {...a11yProps(0)} />
    <Tab label="Reels" {...a11yProps(1)} />
    <Tab label="Carrousel" {...a11yProps(2)} />
    <Tab label="Images" {...a11yProps(3)} />
  </Tabs>
</Box>
        <div className='container'>
            <div style={{ display: 'flex', justifyContent: 'center', flexWrap: 'wrap' }}>
        <Post posts={currentPosts} loading={loading} />
        </div>
        <Pagination length={posts.length} postsPerPage={postsPerPage} handlePagination={handlePagination} currentPage={currentPage} />
      </div>
      </div>
      )}
  
  export default Posts;
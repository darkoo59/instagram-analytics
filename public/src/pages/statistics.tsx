import axios from 'axios';
import * as React from 'react';
import {UserContextProvider, UserContextValue, UserContext} from "../shared/contexts/UserContext"
import Box from '@mui/material/Box/Box';
import Tabs from '@mui/material/Tabs/Tabs';
import Tab from '@mui/material/Tab/Tab';
import { PieChart } from '@mui/x-charts';

const Statistics = () => {
    interface TabPanelProps {
        children?: React.ReactNode;
        index: number;
        value: number;
      }

    const [loading, setLoading] = React.useState(false);
    const { isAuth } = React.useContext<UserContextValue>(UserContext);
    const [value, setValue] = React.useState(0);
    const [currentChart, setCurrentChart] = React.useState("percentages");
    const [data, setData] = React.useState<{ label: string; value: number }[]>([]);
    const [engagementCarousel, setEngagementCarousel] = React.useState<{ label: string; value: number }[]>([]);
    const [engagementReels, setEngagementReels] = React.useState<{ label: string; value: number }[]>([]);
    const [engagementImage, setEngagementImage] = React.useState<{ label: string; value: number }[]>([]);

    const fetchPerchentages = (async () => {
        setLoading(true)
        setCurrentChart("percentages")
        const response = await axios.get("/statistics/percentages", {params: {token: localStorage.getItem("2w3e8oi9pjuthyf4")}});
        const percentages = response.data.percentages;
        console.log(percentages)
        const formattedData = Object.keys(percentages).map(label => ({ label, value: percentages[label] }));
        setData(formattedData);
        setLoading(false);
      });

      const fetchReach = (async () => {
        setLoading(true)
        setCurrentChart("reach")
        const response = await axios.get("/statistics/reach", {params: {token: localStorage.getItem("2w3e8oi9pjuthyf4")}});
        const percentages = response.data.percentages;
        const formattedData = Object.keys(percentages).map(label => ({ label, value: percentages[label] }));
        setData(formattedData);
        setLoading(false);
      });

      const fetchEngagement = (async () => {
        setLoading(true)
        setCurrentChart("engagement")
        const response = await axios.get("/statistics/engagement", {params: {token: localStorage.getItem("2w3e8oi9pjuthyf4")}});
        const carouselData = response.data.percentages["IG carousel"];
        const formattedCarouselData = Object.keys(carouselData).map(label => ({ label: label + " carousel", value: carouselData[label] }));
        setEngagementCarousel(formattedCarouselData);
        const reelsData = response.data.percentages["IG reel"];
        const formattedReelsData = Object.keys(reelsData).map(label => ({ label: label + " reels", value: reelsData[label] }));
        setEngagementReels(formattedReelsData);
        const imageData = response.data.percentages["IG image"];
        const formattedImageData = Object.keys(imageData).map(label => ({ label: label + " image", value: imageData[label] }));
        setEngagementImage(formattedImageData);
        const combinedData = [...formattedCarouselData, ...formattedReelsData, ...formattedImageData];
        setData(combinedData)
        setLoading(false);
      });

      React.useEffect(() => {
        fetchPerchentages();
      }, []);

      const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        console.log(newValue)
        setValue(newValue);
        if(newValue == 0 && currentChart != "percentages")
            fetchPerchentages()
        else if(newValue == 1 && currentChart != "reach")
          fetchReach()
        else if(newValue == 2 && currentChart != "engagement")
          fetchEngagement()
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
    <Tab label="Proportion of post" {...a11yProps(0)} />
    <Tab label="Average reach" {...a11yProps(1)} />
    <Tab label="Post type engagement" {...a11yProps(2)} />
  </Tabs>
</Box>
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '5%'}}>
          { value == 2 && (
            <PieChart
  series={[
     {data} 
  ]}
  width={800}
  height={400}
/>
          )}
{ value != 2 &&        (<PieChart
  series={[
    {data}
  ]}
  width={800}
  height={400}
/>)}
      </div>
      </div>
      )}
  
  export default Statistics;